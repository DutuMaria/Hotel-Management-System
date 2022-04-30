package service;

public class ReadFromFileService {
    public static ReadFromFileService readFromFileService;

    private ReadFromFileService() {}

    public static ReadFromFileService getReadFromFileService() {
        if (readFromFileService == null)
            readFromFileService = new ReadFromFileService();
        return readFromFileService;
    }

}
