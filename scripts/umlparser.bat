@echo off
title UmlParser

REM Storing test folder and output file name in variable for reuse
set input_folder=%1
set output_filename=%~2

REM Echoing vars
echo Initializing uml parser!
echo. 
echo Input folder is : %input_folder%
echo Output file name is: %output_filename%
echo. 

REM Using the Javaparser code to generate text file for plantuml
java -jar umlparser.jar %input_folder%

REM Using the generated text file to create an Image of the UML diagram
type plantuml.txt | java -jar plantuml.jar -pipe > %output_filename%