@echo off

REM Loop through all directories in the current directory
for /d %%D in (*) do (
    cd %%D
    echo Processing directory: %%D
    
    REM Check if pom.xml file exists in the directory
    if exist pom.xml (
        REM Execute 'mvn clean install'
        echo Executing 'mvn clean install' in %%D
        mvn clean install
        
        REM Execute 'mvn clean package'
        echo Executing 'mvn clean package' in %%D
        mvn clean package
    ) else (
        echo No pom.xml file found in %%D. Skipping Maven commands.
    )
    
    cd ..
)
