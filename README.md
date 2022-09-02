# Fiesta
[![Maven Central][mcBadge]][mcLink] [![License][liBadge]][liLink]

A simple java annotations library with compile stage processing.
## Required

JDK 1.8 or higher.

## Getting Started
To add dependencies on Fiesta using Maven, use the following:

```xml
<dependencies>
    <!-- Annotation Module -->
    <dependency>
        <groupId>space.lingu.fiesta</groupId>
        <artifactId>fiesta-annotations</artifactId>
        <version>0.2.0</version>
    </dependency>
    
    <!-- Compile-time Annotation Check Module -->
    <!-- Optional -->
    <dependency>
        <groupId>space.lingu.fiesta</groupId>
        <artifactId>fiesta-checker</artifactId>
        <version>0.2.0</version>
        <scope>provided</scope>
    </dependency>
    
    
</dependencies>
```
> If you don't see warning output when compiling your project via maven,
set `<showWarnings>true<showWarnings>` in the maven-compiler-plugin configuration.

Or using Gradle:

```gradle
dependencies {
    implementation("space.lingu.fiesta:fiesta-annotations:0.2.0")
    
    // optional
    compileOnly("space.lingu.fiesta:fiesta-checker:0.2.0")
}
```


# Licence

```text
   Copyright (C) 2022 Lingu

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```

[liBadge]: https://img.shields.io/github/license/Roll-W/fiesta?color=569cd6&style=flat-square
[liLink]: https://github.com/Roll-W/fiesta/blob/master/LICENSE
[mcBadge]: https://img.shields.io/maven-central/v/space.lingu.fiesta/fiesta-parent?style=flat-square
[mcLink]: https://search.maven.org/search?q=g:space.lingu.fiesta
