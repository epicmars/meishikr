#! /usr/bin/env groovy

void insertLayout(){
    String path = "";
    String start = "<layout\n" +
            "    xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
            "    xmlns:app=\"http://schemas.android.com/apk/res-auto\"\n" +
            "    xmlns:tools=\"http://schemas.android.com/tools\">";

    String end = "</layout>";

    File dir = new File(path);
    println(dir.getPath());
    File[] files = dir.listFiles();
    for(File f : files){
        println(f.getName());
        BufferedReader reader = new BufferedReader(new FileReader(f));
        String firstLine = reader.readLine();
        StringBuilder remain = new StringBuilder();
        String line;
        while((line = reader.readLine()) != null){
            remain.append(line);
        }
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(f)));
        out.append(firstLine);
        out.append(start);
        out.append(remain.toString());
        out.append(end);

        reader.close();
        out.close();
    }
}

println "start"
insertLayout();
println "complete!"
