#!/bin/bash

# Find all directories containing a pom.xml file (assuming these are Maven projects)
find . -type f -name pom.xml -printf '%h\n' | while read -r dir; do
    echo "Processing Maven project in directory: $dir"
    (
        cd "$dir" || exit 1
        echo "Executing 'mvn clean install' in $dir"
        mvn clean install
        
        echo "Executing 'mvn clean package' in $dir"
        mvn clean package
    )
done
