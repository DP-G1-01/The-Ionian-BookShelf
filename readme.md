# Proyecto The Ionian Proyect para la asignatura DP2

Desde un fork de https://github.com/spring-projects/spring-petclinic. 

 - Todas las pruebas se pueden realizar de forma automática desde el perfil de ejecución compatible con MySQL, salvo las de interfaz de usuario, que es necesario ir realizando manualmente la ejecución de cada una integrando el driver a utilizar con su respectiva ruta.
 
 - Hemos tenido numerosos problemas de "compatibilidad" con la base de datos MySQL. Por ello, y tras numerosos cambios, durante la ejecución de las pruebas y en el despliegue de la aplicación en la consola se presentan varios mensajes de error. Sin embargo, estos no interfieren en la ejecución en sí misma, finalizando todo de manera correcta independientemente de estos mensajes.
 
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

- Sobre los tests de UI, comentar que salvo en los tests de baneo de Summoners y todos los relacionados con ChangeRequest y Build, que se ejecutan de forma separada en un puerto independiente, todos los tests deben ejecutarse con la aplicación iniciada manualmente sobre el puerto 8080 y se debe de reiniciar la aplicación al final de cada uno de ellos, para que los efectos de algunos tests no interfieran en el resultado de otros.

- El enlace con todo el contenido extra que se ha ido recopilando a lo largo de desarrollo de la asignatura es el siguiente: https://drive.google.com/drive/u/0/folders/1qTZ3GBc5iL9cPtoe6fdVbKVCDqd4fw65

- Para ejecutar el test de Cucumber quizás sea necesario convertir el proyecto a proyecto Cucumber con click derecho.

- La configuración de Travis está realizada correctamente pero, por los errores comentados previamente, el log llega al límite y no se puede terminar la ejecución de las pruebas.

- El proyecto está preparado para ser lanzadas todas las pruebas con MySQL, para realizar maven install o cualquier comando de maven que necesite de h2 es necesario excluir en el pom todas las pruebas terminadas en UITest y en APITest, aparte de comentar en los tests de servicios la anotación que fuerza que se realicen con MySQL.
