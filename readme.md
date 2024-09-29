compilar: javac -cp "lib/*" src/GraficoEjemplo.java
ejecutar: java -cp "lib/*;src" src/GraficoEjemplo.java

compilar todos los archivos java: 
    javac -cp "lib/jcommon-1.0.23.jar;lib/jfreechart-1.5.3.jar;." src/*.java

ejecutar experimento:
    java -cp "lib/jcommon-1.0.23.jar;lib/jfreechart-1.5.3.jar;." src.ExperimentacionHashing

