Common Lab Module
==========================

Description
-----------
This is a simple module, which can be used to create and customize forms to manage patients' laboratory test orders, samples and results.
The module has been 

The module is built and tested on legacy-UI.

Building from Source
--------------------
You will need to have Java 1.8+ and Maven 2.1.x+ installed.  Use the command `mvn package` to 
compile and package the module.  The .omod file will be in the `omod/target` folder.

Alternatively you can add the snippet provided in the [Creating Modules](https://wiki.openmrs.org/x/cAEr) page to your 
`omod/pom.xml` and use the `mvn` command:

    mvn package -P deploy-web -D deploy.path="../../openmrs-1.8.x/webapp/src/main/webapp"

It will allow you to deploy any changes to your web 
resources such as jsp or js files without re-installing the module. The deploy path says 
where OpenMRS is deployed.

Installation
------------
1. Build the module to produce the `.omod` file.
2. Use the OpenMRS Administration > Manage Modules screen to upload and install the `.omod` file.

If uploads are not allowed from the web (changeable via a runtime property), you can drop the omod
into the `~/.OpenMRS/modules` folder.  (Where `~/.OpenMRS` is assumed to be the Application 
Data Directory that the running openmrs is currently using.)  After putting the file in there 
simply restart OpenMRS/tomcat and the module will be loaded and started.

Wiki
------------
Complete documentation can be found on OpenMRS Wiki page for [Common Lab Module](https://wiki.openmrs.org/display/docs/Common+Lab+Module)
