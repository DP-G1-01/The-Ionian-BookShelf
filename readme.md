# Proyecto The Ionian Proyect para la asignatura DP2

Desde un fork de https://github.com/spring-projects/spring-petclinic. 

 - Todas las pruebas se pueden realizar de forma automática desde el perfil de ejecución compatible con MySQL, salvo las de interfaz de usuario, que es necesario ir realizando manualmente la ejecución de cada una eligiendo , o descomentando el driver del navegador que se vaya a usar con su ruta.
 
 - Hemos tenido numerosos problemas de 'compatibilidad' con la base de datos MySQL por ello, y tras numerosos cambios, la consola mediante la ejecución de las pruebas o de la aplicación presenta numerosos mensajes de error, éstos no perjudican la ejecución en si.
 
 - El script que hemos utilizado para generar la base de datos es el siguiente y lo hemos ejecutado desde MySQL Workbench
```
drop database if exists theionianbookshelf;
create database theionianbookshelf;

drop user if exists 'theionianbookshelf'@'%';
create user 'theionianbookshelf'@'%' identified with mysql_native_password by 'theionianbookshelf';
revoke all privileges, grant option from 'theionianbookshelf'@'%';

grant select, insert, update, delete, create, drop, references, index, alter, 
        create temporary tables, lock tables, create view, create routine, 
        alter routine, execute, trigger, show view
    on theionianbookshelf.* to 'theionianbookshelf'@'%';
```

- El puerto donde se lanzará la aplicación tal y como está ahora mismo es el siguiente: http://localhost:80
