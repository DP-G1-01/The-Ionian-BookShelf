<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="runePages">
	<jsp:attribute name="customScript">
        <script>
            function getMainBranch() {
                var selectedMainBranch = mainBranch.options[mainBranch.selectedIndex];
            }
            function getSecondaryBranch() {
                var selectedSecondaryBranch = secondaryBranch.options[secondaryBranch.selectedIndex];
            }
        </script>
    </jsp:attribute>
    <jsp:body>
	<h2>
		<c:if test="${runePage['new']}">New </c:if>
		Rune Page
	</h2>
	<form:form modelAttribute="runePage" class="form-horizontal"
		id="add-rune-page-form" action="/runePages/save">
		<div class="form-group has-feedback">
		<form:input type="hidden" class="form-control" path="summoner" />
			<petclinic:inputField label="Name" name="name" />
			<div class="control-group">
                   <petclinic:selectField onchange="changeSecondarySelect(this)" name="mainBranch" label="Main Branch" names="${branches}" size="3"/>
            </div>
            <div id="secondaryBranchDiv" class="hidden control-group">
                    <petclinic:selectField onchange="showSecondaryNodes(this)" id="secondaryBranch" name="secondaryBranch" label="Secondary Branch" names="${branches}" size="2"/>
            </div>
            <c:forEach var="runeList" items="${runes}" varStatus="loop">
            <div id="${loop.index/4}" class="hidden control-group">
            	<c:if test="${(loop.index)%4==0}">
            	
        		<petclinic:selectField id="select ${loop.index/4}" name="keyRune" label="Key Rune" names="${runeList}" size="4"/>

        		</c:if>
        		<c:if test="${(loop.index)%4==1}">
            	
        		<petclinic:selectField id="select ${loop.index/4}" name="mainRune1" label="First Main Rune" names="${runeList}" size="3"/>

        		</c:if>
        		<c:if test="${(loop.index)%4==2}">
            	
        		<petclinic:selectField id="select ${loop.index/4}" name="mainRune2" label="Second Main Rune" names="${runeList}" size="3"/>

        		</c:if>
        		<c:if test="${(loop.index)%4==3}">
            	
        		<petclinic:selectField id="select ${loop.index/4}" name="mainRune3" label="Third Main Rune" names="${runeList}" size="3"/>

        		</c:if>
        		</div>
   			</c:forEach>
   			 			
	<c:forEach var="runeList" items="${secondaryRunes}" varStatus="loop">
	<div id="sec1_${loop.index}" class="hidden control-group">
		<div class="form-group ">
	   		<label class="col-sm-2 control-label">First Secondary Rune</label>
	   		<div class="col-sm-10">
	   		<select id="sec1_${loop.index}_sel" name="secRune1" class="form-control" onchange="updateSecRune2(this)" size="3">
	        <c:forEach var="rune" items="${runeList}">
	        <option id="${rune.node}" value="${rune.name}">${rune.name}</option>
	        </c:forEach>
	        </select>
	        </div>
		</div>
	</div>
	<div id="sec2_${loop.index}" class="hidden control-group">
		<div class="form-group ">
	   		<label class="col-sm-2 control-label">Second Secondary Rune</label>
	   		<div class="col-sm-10">
	   		<select id="sec2_${loop.index}_sel" name="secRune2" class="form-control" size="3">
	        </select>
	        </div>
		</div>
	</div>
</c:forEach>		
		</div>
		<div class="form-group">
			<input type="hidden" name="id" value="${runePage.id}" />
			<div class="col-sm-offset-2 col-sm-10">
				<c:choose>
					<c:when test="${runePage['new']}">
						<button class="btn btn-default" type="submit">Add Rune Page</button>
					</c:when>
					<c:otherwise>
						<button class="btn btn-default" type="submit">Update Rune Page</button>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</form:form>
	</jsp:body>
</petclinic:layout>
<script>
function updateSecRune2(runes) {
	var select = runes[runes.selectedIndex];
	var nodo = select.id;
	var id = runes.parentNode.parentNode.parentNode.id;
	console.log(id);
	var i;
	var l=runes.options.length;
	 
	if(id === "sec1_0") {
		for(i=0; i<l; i++) {
		document.getElementById("sec2_0_sel").options.remove(0);
		}
		
		for(i=0; i<l; i++) {
			if(runes.options[i].id != nodo) {
				var aux = document.createElement("option");
				aux.text = runes.options[i].text;
				aux.value = runes.options[i].text;
				document.getElementById("sec2_0_sel").options.add(aux, i);
			}
		}
		
		document.getElementById("sec2_0").classList.remove("hidden");
		document.getElementById("sec2_1").classList.add("hidden");
		document.getElementById("sec2_2").classList.add("hidden");
		
		document.getElementById("sec2_0_sel").disabled = false;
		document.getElementById("sec2_1_sel").disabled = true;
		document.getElementById("sec2_2_sel").disabled = true;
	} else if(id === "sec1_1") {
		for(i=0; i<l; i++) {
			document.getElementById("sec2_1_sel").options.remove(0);
			}
			
			for(i=0; i<l; i++) {
				if(runes.options[i].id != nodo) {
					var aux = document.createElement("option");
					aux.text = runes.options[i].text;
					aux.value = runes.options[i].text;
					document.getElementById("sec2_1_sel").options.add(aux, i);
				}
			}
			
			document.getElementById("sec2_0").classList.add("hidden");
			document.getElementById("sec2_1").classList.remove("hidden");
			document.getElementById("sec2_2").classList.add("hidden");
			
			document.getElementById("sec2_0_sel").disabled = true;
			document.getElementById("sec2_1_sel").disabled = false;
			document.getElementById("sec2_2_sel").disabled = true;
	} else if(id === "sec1_2") {
		for(i=0; i<l; i++) {
			document.getElementById("sec2_2_sel").options.remove(0);
			}
			
			for(i=0; i<l; i++) {
				if(runes.options[i].id != nodo) {
					var aux = document.createElement("option");
					aux.text = runes.options[i].text;
					aux.value = runes.options[i].text;
					document.getElementById("sec2_2_sel").options.add(aux, i);
				}
			}
			
			document.getElementById("sec2_0").classList.add("hidden");
			document.getElementById("sec2_1").classList.add("hidden");
			document.getElementById("sec2_2").classList.remove("hidden");
			
			document.getElementById("sec2_0_sel").disabled = true;
			document.getElementById("sec2_1_sel").disabled = true;
			document.getElementById("sec2_2_sel").disabled = false;
	}
} 

function changeSecondarySelect(primary) {
	var main = primary.value;
	var secondary = document.getElementById("secondaryBranch");
	var aux = document.createElement("option");
	var aux2 = document.createElement("option");
	/* 
	var options = primary.options;
	console.log(options); */

	var i;
	var l =secondary.options.length;
	for(i=0; i<l; i++) {
		secondary.options.remove(0);
	}
	
	
	if(main === primary.options[0].text) {

		aux.text=primary.options[1].text;
		aux.value=primary.options[1].text;
		secondary.options.add(aux, 0);
		
		aux2.text=primary.options[2].text;
		aux2.value=primary.options[2].text;
		secondary.options.add(aux2, 1);

	 	document.getElementById("0.0").classList.remove("hidden");
		document.getElementById("0.25").classList.remove("hidden");
		document.getElementById("0.5").classList.remove("hidden");
		document.getElementById("0.75").classList.remove("hidden");
		document.getElementById("1.0").classList.add("hidden");
		document.getElementById("1.25").classList.add("hidden");
		document.getElementById("1.5").classList.add("hidden");
		document.getElementById("1.75").classList.add("hidden");
		document.getElementById("2.0").classList.add("hidden");
		document.getElementById("2.25").classList.add("hidden");
		document.getElementById("2.5").classList.add("hidden");
		document.getElementById("2.75").classList.add("hidden"); 
		
		document.getElementById("select 0.0").disabled = false;
		document.getElementById("select 0.25").disabled = false;
		document.getElementById("select 0.5").disabled = false;
		document.getElementById("select 0.75").disabled = false;
		document.getElementById("select 1.0").disabled = true;
		document.getElementById("select 1.25").disabled = true;
		document.getElementById("select 1.5").disabled = true;
		document.getElementById("select 1.75").disabled = true;
		document.getElementById("select 2.0").disabled = true;
		document.getElementById("select 2.25").disabled = true;
		document.getElementById("select 2.5").disabled = true;
		document.getElementById("select 2.75").disabled = true;

		
	} else if(main === primary.options[1].text) {

		aux.text=primary.options[0].text;
		aux.value=primary.options[0].text;
		secondary.options.add(aux, 0);
		
		aux2.text=primary.options[2].text;
		aux2.value=primary.options[2].text;
		secondary.options.add(aux2, 1);
		
		document.getElementById("0.0").classList.add("hidden");
		document.getElementById("0.25").classList.add("hidden");
		document.getElementById("0.5").classList.add("hidden");
		document.getElementById("0.75").classList.add("hidden");
		document.getElementById("1.0").classList.remove("hidden");
		document.getElementById("1.25").classList.remove("hidden");
		document.getElementById("1.5").classList.remove("hidden");
		document.getElementById("1.75").classList.remove("hidden");
		document.getElementById("2.0").classList.add("hidden");
		document.getElementById("2.25").classList.add("hidden");
		document.getElementById("2.5").classList.add("hidden");
		document.getElementById("2.75").classList.add("hidden");
		
		document.getElementById("select 0.0").disabled = true;
		document.getElementById("select 0.25").disabled = true;
		document.getElementById("select 0.5").disabled = true;
		document.getElementById("select 0.75").disabled = true;
		document.getElementById("select 1.0").disabled = false;
		document.getElementById("select 1.25").disabled = false;
		document.getElementById("select 1.5").disabled = false;
		document.getElementById("select 1.75").disabled = false;
		document.getElementById("select 2.0").disabled = true;
		document.getElementById("select 2.25").disabled = true;
		document.getElementById("select 2.5").disabled = true;
		document.getElementById("select 2.75").disabled = true;
		
	} else if(main === primary.options[2].text) {

		aux.text=primary.options[0].text;
		aux.value=primary.options[0].text;
		secondary.options.add(aux, 0);
	
		aux2.text=primary.options[1].text;
		aux2.value=primary.options[1].text;
		secondary.options.add(aux2, 1);
		
		document.getElementById("0.0").classList.add("hidden");
		document.getElementById("0.25").classList.add("hidden");
		document.getElementById("0.5").classList.add("hidden");
		document.getElementById("0.75").classList.add("hidden");
		document.getElementById("1.0").classList.add("hidden");
		document.getElementById("1.25").classList.add("hidden");
		document.getElementById("1.5").classList.add("hidden");
		document.getElementById("1.75").classList.add("hidden");
		document.getElementById("2.0").classList.remove("hidden");
		document.getElementById("2.25").classList.remove("hidden");
		document.getElementById("2.5").classList.remove("hidden");
		document.getElementById("2.75").classList.remove("hidden");
		
		document.getElementById("select 0.0").disabled = true;
		document.getElementById("select 0.25").disabled = true;
		document.getElementById("select 0.5").disabled = true;
		document.getElementById("select 0.75").disabled = true;
		document.getElementById("select 1.0").disabled = true;
		document.getElementById("select 1.25").disabled = true;
		document.getElementById("select 1.5").disabled = true;
		document.getElementById("select 1.75").disabled = true;
		document.getElementById("select 2.0").disabled = false;
		document.getElementById("select 2.25").disabled = false;
		document.getElementById("select 2.5").disabled = false;
		document.getElementById("select 2.75").disabled = false;
		
	}

	document.getElementById("secondaryBranchDiv").classList.remove("hidden");
	document.getElementById("sec1_0").classList.add("hidden");
	document.getElementById("sec2_0").classList.add("hidden");
	document.getElementById("sec1_1").classList.add("hidden");
	document.getElementById("sec2_1").classList.add("hidden");
	document.getElementById("sec1_2").classList.add("hidden");
	document.getElementById("sec2_2").classList.add("hidden");
	document.getElementById("sec1_0_sel").disabled = true;
	document.getElementById("sec2_0_sel").disabled = true;
	document.getElementById("sec1_1_sel").disabled = true;
	document.getElementById("sec2_1_sel").disabled = true;
	document.getElementById("sec1_2_sel").disabled = true;
	document.getElementById("sec2_2_sel").disabled = true;
}

function showSecondaryNodes(secondary) {
	var main = secondary.value;
	
	if(main === "Precision") {
		
		document.getElementById("sec1_0").classList.remove("hidden");
		document.getElementById("sec1_1").classList.add("hidden");
		document.getElementById("sec1_2").classList.add("hidden");
		
		document.getElementById("sec1_0_sel").disabled = false;
		document.getElementById("sec1_1_sel").disabled = true;
		document.getElementById("sec1_2_sel").disabled = true;
		
	} else if(main === "Domination") {

		document.getElementById("sec1_0").classList.add("hidden");
		document.getElementById("sec1_1").classList.remove("hidden");
		document.getElementById("sec1_2").classList.add("hidden");
		
		document.getElementById("sec1_0_sel").disabled = true;
		document.getElementById("sec1_1_sel").disabled = false;
		document.getElementById("sec1_2_sel").disabled = true;
		
	} else if(main === "Resolve") {

		document.getElementById("sec1_0").classList.add("hidden");
		document.getElementById("sec1_1").classList.add("hidden");
		document.getElementById("sec1_2").classList.remove("hidden");
		
		document.getElementById("sec1_0_sel").disabled = true;
		document.getElementById("sec1_1_sel").disabled = true;
		document.getElementById("sec1_2_sel").disabled = false;
		
	}

	document.getElementById("sec2_0").classList.add("hidden");
	document.getElementById("sec2_1").classList.add("hidden");
	document.getElementById("sec2_2").classList.add("hidden");
	
	document.getElementById("sec2_0_sel").disabled = true;
	document.getElementById("sec2_1_sel").disabled = true;
	document.getElementById("sec2_2_sel").disabled = true;
}
</script>