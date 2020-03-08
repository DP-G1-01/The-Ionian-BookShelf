CREATE DATABASE IF NOT EXISTS theionianbookshelf;

ALTER DATABASE petclinic
  DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_general_ci;

GRANT ALL PRIVILEGES ON theionianbookshelf.* TO 'theionianbookshelf@%' IDENTIFIED BY 'theionianbookshelf';
