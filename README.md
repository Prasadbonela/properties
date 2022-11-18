# Properties

The purpose of this exercise is to train you to use `Properties` class.  
Duration _45 minutes_.

## Description

In this task, you need to determine a class which can operate several properties files. One of them is the main properties file and others are an ordereded list of default properties.

There is a file **config.properties** with properties, and it **may** contain property
~~~
default.filenames = default1,default2,default3,...
~~~
where default1, default2, default3, ... are names of properties files (without extension _.properties_).

`Config` class implements logic to work with all properties that are contained in files:
~~~
config.properties
default1.properties
default2.properties
default3.properties
...
~~~

Objects of `Config` use properties from **defaultX.properties** as default properties.

> Names **default1**, **default2**, **default3**, ... are given for a example. In the common case names of properties files must be obtain dynamically from the **config.properties** file.

Please, proceed to `Config` class and implement its content:  

* `public Config()`
initializes a `Config` object;

* `public void reset()`
reinitializes this `Config` object;

* `public String get(String key)`
returns the value by the specified _key_;

* `public void remove(String key)`
removes the property by the specified `key`, if no such property does nothing;

* `public void set(String key, String value)`
sets the property with the specified _key_ and _value_;

* `public void save()`
saves properties.

## Details

For a example the config file **config.properties** contains property 
~~~
default.filenames = default1,default2,default3
~~~

* `public void reset()`  
Sets this `Config` object's state to the initial state as if it had just been created.

* `public String get(String key)`  
Searches and returns the value by the specified `key`. The search process is performed in the following order:  
 - **config.properties**  
 - **default1.properties**  
 - **default2.properties**  
 - **default3.properties**  
 
 Returns `null` if no property with the specified key is found.

* `public void remove(String key)`
Removes the property with the specified `key` if it previously has been read from the **config.properties** file or has been set with the help of the `set` method.

* `public void set(String key, String value)`  
Sets the property with the specified key and value. This property will be saved in **config.properties** if the `save` method will be invoked. 

* `public void save()`  
Saves all the current properties in the **config.properties** file.
Current properties are the following:
 - obtained from the **config.properties** file;
 - added with the help of the `set` method;
 - except those that are removed by the `remove` method.

Pay attention to the constructor  
~~~
public java.util.Properties(Properties defaultProps)
~~~
It can be used to construct the `Properties` object with the given default properties recursively.

## Restrictions

You have to use just only one class from java.util package: `Properties`.  

> It is allowed to add throws clause to methods of `Config` class if test methods allow it.

## Example
Let's say there are following property files:  

**config.properties**  
~~~
default.filenames = default1,default2
key1 = A
~~~

**default1.properties**  
~~~
key1 = B
~~~

**default2.properties**  
~~~
key1 = C
key2 = D
~~~

The following code generates the appropriate output:
~~~
Config config = new Config();
		
System.out.println(config.get("key1")); // A
System.out.println(config.get("key2")); // D
config.remove("key1");
System.out.println(config.get("key1")); // B

config.save();
System.out.println(config.get("key1")); // B
		
config.set("default.filenames", "default2");
config.save();
config.reset();
System.out.println(config.get("key1")); // C
~~~

As a result, **default1.properties** and **default2.properties** have the content as before, but the content of **config.properties** is:
~~~
default.filenames = default2	
~~~
