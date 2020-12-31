package com.dare.videoQuery.dao;

import com.dare.videoQuery.model.VideoObject;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;


@Repository("videoDao")
public class VideoAccessService implements EditResultDao, DisplayResultDao {

    private static List<String> FrameList = new ArrayList<String>();
    ArrayList<VideoObject[]> objList1 = new ArrayList<VideoObject[]>();
    ArrayList<VideoObject[]> objList = new ArrayList<VideoObject[]>();
    private static int objCount =  0;
    private static int correctionCount = 0;
    private static int frameCount = -1;
    private static int listCount=0;
    private static int listCount2=0;
    private static int listCount3=0;



    @Override
    public int editVideoResult(int objectId, String objectType, int objectTypeCode, int frameNumber, int newObjectId) {

        for (VideoObject[] videoObject : objList) {
            for (int i = 0; i < videoObject.length; i++) {
                if (videoObject[i].getObjectId() == objectId && videoObject[i].getFrameNumber() == frameNumber) {
                    videoObject[i].setObjectId(newObjectId);
                    videoObject[i].setObjectType(objectType);
                    videoObject[i].setObjectTypeCode(objectTypeCode);
                    correctionCount++;
                }
            }
        }

        return 1;
    }

    @Override
    public List getVideoResult() throws FileNotFoundException {
        if(listCount3 == 0){
            createList(objList1);
            listCount3++;
        }
        return objList1;
    }

    @Override
    public List getCorrectedResult() throws FileNotFoundException {
        if(listCount2 == 0){
            createList(objList);
            listCount2++;
        }
        return objList;
    }


    @Override
    public int createList(List objectList) throws FileNotFoundException {
        List<String> PathList = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(Paths.get("/Users/user/Downloads/videoQuery/src/main/resources/public.ids/ids/"))) {
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    try {
                        String fileString = filePath.toString();
                        PathList.add(fileString);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.sort(PathList);
        for (int i = 0; i < PathList.size(); i++) {
            Scanner scan1 = new Scanner(new File(PathList.get(i)));
            List<VideoObject> inner = new ArrayList<>();
            frameCount++;
            while (scan1.hasNextLine()) {
                int objectId, objectTypeCode;
                double score;
                String objectType, line;
                int[] coordinates;
                line = scan1.nextLine();
                String[] strArray = line.split(";");
                objectId = Integer.parseInt(strArray[0]);
                objectType = strArray[1].trim();
                objectTypeCode = Integer.parseInt(strArray[2].trim());
                String str = strArray[3].trim();
                coordinates = Arrays.stream(str.substring(1, str.length() - 1).split(","))
                        .map(String::trim).mapToInt(Integer::parseInt).toArray();
                score = Double.parseDouble(strArray[4].trim());
                VideoObject object1 = new VideoObject(objectId, objectType, objectTypeCode, frameCount, coordinates, score);
                inner.add(object1);
                objCount++;
            }
            VideoObject[] objArray = new VideoObject[inner.size()];
            for (int j = 0; j < objArray.length; j++) {
                objArray[j] = inner.get(j);
            }
            objectList.add(objArray);
        }

        return 1;
    }

    @Override
    public List leaseVideo() {
            if(listCount == 0) {
                String servingURL = "http://localhost:8080";
                try (Stream<Path> paths = Files.walk(Paths.get("/Users/user/Downloads/videoQuery/src/main/resources/public/frames/"))) {
                    paths.forEach(filePath -> {
                        if (Files.isRegularFile(filePath)) {
                            try {
                                File fileURL;
                                fileURL = filePath.toFile();
                                FrameList.add(servingURL + fileURL.toString().replace("/Users/user/Downloads/videoQuery/src/main/resources/public", ""));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
                Collections.sort(FrameList);
                FrameList.remove(0);
                listCount++;
            }
        return FrameList;
    }
    public double getErrorRate(){
        return (double)correctionCount/objCount;
    }
}