package cn.dawnland;

import cn.dawnland.data.ExecuteResult;
import cn.dawnland.utils.HttpUtils;
import cn.dawnland.utils.VersionUtils;
import cn.dawnland.vo.VersionResponse;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    private static final List<VersionResponse> versions = VersionUtils.getVersions();
    private static final File dataFile = new File("version.dat");
    private static final String prefix = "[黎明娘] - ";

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        print("初始化完成");
        ObjectInputStream ois = null;
        try {
            FileInputStream fis = new FileInputStream(dataFile);
            ois = new ObjectInputStream(fis);
        } catch (Exception e) {
            print("未找到历史执行记录，将按照基准执行");
        }
        List<ExecuteResult> results = dataFile.exists() ? (List<ExecuteResult>) ois.readObject() : new ArrayList<>();
        List<Integer> beenExecutedVersion = results.stream().map(ExecuteResult::getNumberVersion).collect(Collectors.toList());
        List<VersionResponse> untreated = versions.stream().filter(v -> !beenExecutedVersion.contains(v.getNumberVersion())).collect(Collectors.toList());
        untreated.forEach(v -> {
            print("正在执行" + v.getDisplayVersion() + "更新");
            if(v.getFiles() != null && v.getFiles().size() > 0){
                v.getFiles().forEach((key, value) -> {
                    String[] split = key.split("/");
                    try {
                        Files.createDirectories(Paths.get(key.replace(split[split.length - 1], "")));
                        File file = Paths.get(key).toFile();
                        FileOutputStream fos = new FileOutputStream(file);
                        byte[] fileByte = HttpUtils.INSTANCE.getFile(value);
                        fos.write(fileByte);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
            try {
                Process exec = Runtime.getRuntime().exec("cmd.exe /c " + v.getCommand());
                print(v.getDescription());
                BufferedReader bf = new BufferedReader(new InputStreamReader(exec.getInputStream(), Charset.forName("GBK")));
                StringBuffer sb = new StringBuffer();
                String line = bf.readLine();
                while(line != null){
                    sb.append(line).append("\n");
                    line = bf.readLine();
                }
                results.add(ExecuteResult.builder()
                        .executeMsg(sb.toString())
                        .numberVersion(v.getNumberVersion())
                        .succeed(true)
                        .time(LocalDateTime.now())
                        .build());
            } catch (Exception e) {
                e.printStackTrace();
            }
            print(v.getDisplayVersion() + "更新成功");
        });
        try(FileOutputStream fos = new FileOutputStream(dataFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos)){
            oos.writeObject(results);
        }
        // TODO: 2021-01-19 调起HMCL
        print("按回车启动");
        new Scanner(System.in).nextLine();
        new ProcessBuilder("启动器.exe").start();
    }

    private static void print(String text){
        System.out.println(prefix + text);
    }

}

