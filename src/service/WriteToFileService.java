package service;

public class WriteToFileService {
    public static WriteToFileService writeToFileService;

    private WriteToFileService() {}

    public static WriteToFileService getWriteToFileService() {
        if (writeToFileService == null)
            writeToFileService = new WriteToFileService();
        return writeToFileService;
    }

}
