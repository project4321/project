# install Gradle 
  1. Open Eclipse
  2. Help > Eclipse Marketplace...
  3. Search "Gradle IDE Pack 3.7.x+1.0.x"
  4. Install it!
  
# import Gradle project
  1. Open Eclipse
  2. File > Import... > Gradle (STS) > Gradle (STS) Project > Browse > Build Model
  3. Select the project in checkbox
  
# download new dependencies
  1. open build.gradle
  2. add compile '....'
  3. Right click project
  4. Gradle (STS) > Refresh All
  
# add jar
> if you don't want to use gradle

  1. Open project properties
  2. Java Build Path > Libraries > Add JARs...
  3. Select COMP4321/lib/<name>.jar
  
# run program
  1. Open EntryPoint.java
  2. Run it
