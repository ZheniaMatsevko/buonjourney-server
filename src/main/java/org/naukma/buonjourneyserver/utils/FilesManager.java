package org.naukma.buonjourneyserver.utils;

import java.io.File;

public class FilesManager {
    private final static String PREFIX_FOR_USER_FOLDER = "user_";
    private final static String PATH_TO_FILE = "/data/" + PREFIX_FOR_USER_FOLDER;
    private final static String ROOT_PATH = new File("").getAbsolutePath() + "\\client\\events-manager\\public\\data";
}
