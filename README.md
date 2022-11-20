# Properties

The purpose of this exercise is to train you to use `Properties` class.  
Duration _45 minutes_.

## Description

In this task, you need to determine a class which can operate several properties files. One of them is the main properties file and others are an ordereded list of default properties.

Main properties file is **config.properties**. It may (or may not) contain the property with name _default.filenames_:
~~~
default.filenames = default1,default2,default3
~~~
where default1, default2, default3 are names of the default properties files (without extension _.properties_).

The `Config` class implements logic to work with all properties that are contained in the main properties file and in the default properties files:
~~~
config.properties
default1.properties
default2.properties
default3.properties
~~~

> Names **default1**, **default2**, **default3** are given for a example. In the common case names of properties files must be obtain dynamically from the **config.properties** file.

Please, proceed to `Config` class and implement its content:  

* `public Config()`  
Initializes a `Config` object.

* `public void reset()`  
Sets this `Config` object's state to the initial state as if it had just been created.

* `public String get(String key)`  
Searches and returns the value by the specified `key`. Returns `null` if there is no property with such `key`. The search process is performed in the following order:  
  - **config.properties**  
  - the first default properties file (if it exists)
  - the second default properties file (if it exists)
  - ...

* `public void remove(String key)`  
Removes the property by the specified `key`. Does nothing if there is no property with such `key`. This method can remove only property that conforms at least one of the following:
  - it previously has been read from the **config.properties** file;
  - it has been set with the help of the `set` method.

* `public void set(String key, String value)`  
Sets the property with the specified _key_ and _value_; this property will be saved in **config.properties** file if the `save` method will be invoked. 

* `public void save()`  
Saves all the current properties in the **config.properties** file. Current properties are the following:
  - all properties obtained from the **config.properties** file;
  - all properties added with the help of the `set` method except those that are removed by using the `remove` method.

## Details

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
