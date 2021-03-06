package org.springframework.samples.the_ionian_bookshelf.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.samples.the_ionian_bookshelf.model.Build;
import org.springframework.samples.the_ionian_bookshelf.model.Champion;
import org.springframework.samples.the_ionian_bookshelf.model.Item;
import org.springframework.samples.the_ionian_bookshelf.model.RunePage;
import org.springframework.samples.the_ionian_bookshelf.model.Summoner;
import org.springframework.samples.the_ionian_bookshelf.model.Thread;
import org.springframework.samples.the_ionian_bookshelf.service.BuildService;
import org.springframework.samples.the_ionian_bookshelf.service.ChampionService;
import org.springframework.samples.the_ionian_bookshelf.service.SummonerService;
import org.springframework.samples.the_ionian_bookshelf.service.ThreadService;
import org.springframework.samples.the_ionian_bookshelf.service.VoteService;
import org.springframework.samples.the_ionian_bookshelf.validators.BuildValidator;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BuildController {

    private final BuildService buildService;

    private final SummonerService summonerService;

//    private final ChampionService championService;
    
    private final ThreadService threadService;
    
    private final VoteService voteService;

    @Autowired
    public BuildController(BuildService buildService, SummonerService summonerService, ThreadService threadService, VoteService voteService) {
        this.buildService = buildService;
        this.summonerService = summonerService;
        this.threadService = threadService;
        this.voteService = voteService;
    }

    @InitBinder("build")
    public void initItemBinder(WebDataBinder dataBinder) {
        dataBinder.setValidator(new BuildValidator());
    }

    @GetMapping(value = "/builds")
    public String listPublicBuilds(ModelMap model) {
    	
    	try {
			Summoner summ = this.summonerService.findByPrincipal();
			if (summ.getBanned() == true) {
				return "redirect:/banned";
			}
		} catch (AssertionError e) {
		} catch (NoSuchElementException e) {
		}
    	
        Collection<Build> builds = this.buildService.findAllPublics();
        for (Build build : builds) {
			build.setPunctuation(voteService.getPunctuationBuild(build));
		}
        model.addAttribute("builds", builds);
        
        return "builds/buildsList";
    }

    @GetMapping(value = "/mine/builds")
    public String listMineBuilds(ModelMap model) {

        try {
            this.summonerService.findByPrincipal();
        } catch (NoSuchElementException u) {
            model.addAttribute("message", "You must be logged in as a summoner");
            return "redirect:/login";
        } catch (AssertionError e) {
            model.addAttribute("message", "You must be logged in as a summoner");
            return "redirect:/";
        }

        Collection<Build> builds = this.buildService.findMineBuilds(this.summonerService.findByPrincipal().getId());
        for (Build build : builds) {
			build.setPunctuation(voteService.getPunctuationBuild(build));
		}
        model.addAttribute("builds", builds);
        return "builds/buildsList";
    }

    @GetMapping("/builds/{buildId}")
    public String showPublicBuild(@PathVariable("buildId") int buildId, ModelMap modelmap) {
    	
    	try {
			Summoner summ = this.summonerService.findByPrincipal();
			if (summ.getBanned() == true) {
				return "redirect:/banned";
			}
		} catch (AssertionError e) {
		} catch (NoSuchElementException e) {
		}
    	
        String view = "";
        Build build = this.buildService.findBuildById(buildId);
        if (build.isVisibility()) {
            view = "builds/editBuild";

            String items = "";
            for (Item i : build.getItems()) {
                if (build.getItems().get(0) == i) {
                    items += i.getTitle();
                } else {
                    items += ", " + i.getTitle();
                }
            }
            items.replace("[", "").replace("]", "");
            modelmap.addAttribute("build", build);
            modelmap.addAttribute("sitems", items);
            modelmap.addAttribute("show", true);
        } else {
            view = "redirect:/builds";
            modelmap.addAttribute("message", "This build is not public.");
        }
        return view;
    }

    @GetMapping("/mine/builds/{buildId}")
    public String showMineBuild(@PathVariable("buildId") int buildId, ModelMap modelMap) {
        try {
            this.summonerService.findByPrincipal();
        } catch (NoSuchElementException u) {
            modelMap.addAttribute("message", "You must be logged in as a summoner");
            return "redirect:/login";
        } catch (AssertionError e) {
            modelMap.addAttribute("message", "You must be logged in as a summoner");
            return "redirect:/";
        }

        String view = "";
        Build build = this.buildService.findBuildById(buildId);
        if (summonerService.findByPrincipal().getId() == build.getSummoner().getId()) {
            view = "builds/editBuild";

            String items = "";
            for (Item i : build.getItems()) {
                if (build.getItems().get(0) == i) {
                    items += i.getTitle();
                } else {
                    items += ", " + i.getTitle();
                }
            }
            items.replace("[", "").replace("]", "");
            modelMap.addAttribute("build", build);
            modelMap.addAttribute("sitems", items);
            modelMap.addAttribute("show", true);
        } else {
            view = "redirect:/mine/builds";
            modelMap.addAttribute("message", "You must be logged in as the summoner who create the build.");
        }
        return view;
    }

    @ModelAttribute("items")
    @Cacheable("populateItems")
    @Transactional(readOnly = true)
    public Collection<Item> populateItems() {
        return this.buildService.findItems();
    }

    @ModelAttribute("champions")
    @Cacheable("populateChampions")
    public Collection<Champion> populateChampions() {
        return this.buildService.findChampions();
    }

    @ModelAttribute("runePages")
    @Cacheable("populateRunePages")
    public Collection<RunePage> populateRunePages() {
        return this.buildService.findRunePages();
    }

    @GetMapping(value = "mine/builds/new")
    public String crearBuild(ModelMap modelMap) {
        try {
            this.summonerService.findByPrincipal();
        } catch (NoSuchElementException u) {
            modelMap.addAttribute("message", "You must be logged in as a summoner");
            return "redirect:/login";
        } catch (AssertionError e) {
            modelMap.addAttribute("message", "You must be logged in as a summoner");
            return "redirect:/";
        }
        String view = "builds/editBuild";
        Build build = new Build();
        List<Item> var = new ArrayList<>();
        Integer summonerId = this.summonerService.findByPrincipal().getId();
        build.setItems(var);
        Summoner summoner = summonerService.findOne(summonerId);
        build.setSummoner(summoner);
        modelMap.addAttribute("build", build);
        modelMap.addAttribute("summonerId", summonerId);
        return view;
    }

    @PostMapping(value = "mine/builds/save")
    public String salvarBuild(@Valid Build build, BindingResult result, ModelMap model) {
    	
        try {
            this.summonerService.findByPrincipal();
        } catch (NoSuchElementException u) {
            model.addAttribute("message", "You must be logged in as an summoner");
            return "redirect:/login";
        } catch (AssertionError e) {
            model.addAttribute("message", "You must be logged in as an summoner");
            return "redirect:/";
        }

        if (result.hasErrors()) {
            model.addAttribute("build", build);
            Integer summonerId = this.summonerService.findByPrincipal().getId();
            model.addAttribute("summonerId", summonerId);
            return "builds/editBuild";
        } else {
            
            buildService.saveBuild(build);
            model.addAttribute("message", "Build save successfully");
        }

        return "redirect:/mine/builds";
    }

    @GetMapping(value = "/mine/builds/{buildId}/remove")
    public String removeBuild(@PathVariable("buildId") int buildId, ModelMap modelMap) {

        try {
            this.summonerService.findByPrincipal();
        } catch (NoSuchElementException u) {
            modelMap.addAttribute("message", "You must be logged in as a summoner");
            return "redirect:/login";
        } catch (AssertionError e) {
            modelMap.addAttribute("message", "You must be logged in as a summoner");
            return "redirect:/";
        }
        Build b = buildService.findBuildById(buildId);
        if (b.getSummoner().getId() == this.summonerService.findByPrincipal().getId()) {
            buildService.removeBuildById(buildId);
        } else {
            modelMap.addAttribute("message", "You must be logged in as the summoner who create the build.");
        }
        return "redirect:/mine/builds";
    }

    @GetMapping(value = "/mine/builds/{buildId}/edit")
    public String initUpdateBuildForm(@PathVariable("buildId") int buildId, Model model) {
        String view = "";

        try {
            this.summonerService.findByPrincipal();
        } catch (NoSuchElementException u) {
            model.addAttribute("message", "You must be logged in as a summoner");
            return "redirect:/login";
        } catch (AssertionError e) {
            model.addAttribute("message", "You must be logged in as a summoner");
            return "redirect:/";
        }

        Build build = buildService.findBuildById(buildId);
        if (summonerService.findByPrincipal().getId() == build.getSummoner().getId()) {
            view = "builds/editBuild";
            model.addAttribute(build);
        } else {
            view = "redirect:/mine/builds";
            model.addAttribute("message", "You must be logged in as the summoner who create the build.");
        }

        return view;
    }

    @PostMapping(value = "/mine/builds/{buildId}/edit")
    public String processUpdateBuildForm(@Valid Build build, BindingResult result,
            @PathVariable("buildId") int buildId) {
        String view = "builds/editBuild";

        try {
            this.summonerService.findByPrincipal();
        } catch (NoSuchElementException u) {
            return "redirect:/login";
        } catch (AssertionError e) {
            return "redirect:/";
        }

        if (result.hasErrors()) {
            view = "builds/editBuild";
        } else {
            build.setId(buildId);

            if (build.isVisibility() == true && build.getThread() == null) {
            	try {
        			Summoner summ = this.summonerService.findByPrincipal();
        			if (summ.getBanned() == true) {
        				return "redirect:/banned";
        			}
        		} catch (AssertionError e) {
        		} catch (NoSuchElementException e) {
        		}
            	
                Thread th = new Thread("Thread of " + build.getTitle(), "Este es el thread publico de la build "
                        + build.getTitle() + ", cuyo autor es " + build.getSummoner().getUser().getUsername(), null);
                threadService.save(th);
                build.setThread(th);
            }

            this.buildService.saveBuild(build);
            view = "redirect:/mine/builds";
        }

        return view;
    }
}
