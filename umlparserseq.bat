@echo on
title UmlParserSequence

set input_folder=%1
set output_filename=%~2

echo "Building Sequence Diagram txt file"
rmdir /s /q temp
mkdir temp

copy SequenceGenerator.aj temp
copy Message.java temp
copy aspectjrt.jar temp
copy %input_folder%\*.java temp


call ajc -cp aspectjrt.jar -1.8 temp\*.java temp\*.aj
cd "D:\Masters\CMPE 202\Projects\Personal Project\code\temp"
java -cp *;. Main
copy plantumlseq.txt ..\
cd ..
type plantumlseq.txt | java -jar plantuml.jar -pipe > %output_filename%
