# Properties

The purpose of this exercise is to train you to use `Properties` class.

Duration: **45 minutes**.


## Description

In this task, you need to describe a class which can operate several properties files. One of them is the main properties file, and others are the sets of the default properties.

The main properties file is named `config.properties`. In addition to a set of properties, this file may contain a property - `default.filenames`, which defines the names of files with default properties.  

For example,  
`default.filenames = default1,default2,default3,...`
where `default1, default2, default3, ...` are names of properties files (without extension .properties).  

Now, please proceed to the `Config` class, which has a config   field of the Properties type, and provide implementations its content: 
* `public Config()`  
   Creates the Config object, which encapsulates a set of properties in the config field, and that have been read from all property’s files
* `public void reset()`  
   Sets an object the `Config` class to the initial state. That is, it reads all properties from `config.properties` file and stores them in the config field. All the default property's files   contains the default properties for that object
* `public String get(String key)`  
   Returns the value by the specified key from the `config` field. If there was no such key in the `config.properties file`, it returns `null` or returns the value of one of the default properties, if it exists.
* `public void set(String key, String value)`  
   Sets a property with the specified key and value to the config field. If there is no such key, it adds property. If the key is present, it replaces the value.
* `public void save()`  
  Saves all current properties   from the `config` field to the `config.properties` file.  However, the default properties are not saved to file.
* `public void remove(String key)`  
  Removes the property with the specified key from the config field. If there is no such key, it does nothing.   


### Details:
*	You are allowed to add the throws section to the descriptions of methods of the Config class that read/write files.
*	The `reset()` method reads a properties from files with default properties in order, starting with the last file specified in the value of the `default.filenames` key and ending with the `config.properties` file.
*	The current properties are:
  - all properties obtained from the original `config.properties` file, except for those removed by the `remove` method
  - all properties added by the `set` method.
*	The `remove` method can delete only property that was read from the original `config.properties` file or set by the `set` method.
*	The constructor of the `Properties` class with parameter the `Properties` type   can be used to recursively create a `Properties` object with given default properties.


## Restrictions
You may not: 
* Use any type from the `java.util` package or its subpackages, except for the `java.util.Properties`
* Add fields to the `Config` class
*	Use lambdas or streams when implementing this task.

## Example
Let's say there are following property’s files and their contents:
config.properties
```
default.filenames = default1,default2
key1 = A

default1.properties
key1 = B

default2.properties
key1 = C
key2 = D
```
And the following code using the Config class:
```java
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
```
The comments on the code lines show the result of executing these lines of code.
The `default1.properties` and `default2.properties` have the content as before, but the content of `config.properties` is:
`default.filenames = default2`

