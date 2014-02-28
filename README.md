=======
punter
======

dummy tomcat/java war repo to use as source for devops practice deploys.  The idea is to roll out tomcat on hosts and then run these configs.  bonus points for running apache in front of them.

Url you'll be hitting is:
```
http://localhost:8080/punter/punt/test
```

There are two files that its looking for: /etc/punt01.properties and /etc/punt02.properties.  If this test gets extended to windows, we'll make those things variable as well.

The expected return value then is 01 will produce the value "static" and will use a static file in the platform being tested.

The expected return value then is 02 will produce the name of the platform ("chef", "puppet", "salt", etc...") and will use a template in the platform being tested.

The output will look something like 

```
[{"key":"conf1","val":"static"},{"key":"conf2","val":"chef"}]
```
