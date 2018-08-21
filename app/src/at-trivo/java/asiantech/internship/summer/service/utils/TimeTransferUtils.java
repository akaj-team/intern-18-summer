package asiantech.internship.summer.service.utils;

public final class TimeTransferUtils {
    private TimeTransferUtils() {
        //no-op
    }

    /**
     * @param millisecond time in millisecond like 1320
     * @return time display in mm:ss like 12:01
     */
    public static String millisecondToClock(int millisecond) {
        int second = millisecond / 1000;
        int minutes = second / 60;
        second %= 60;
        String sec = "" + second;
        if (second < 10) {
            sec = "0" + sec;
        }
        return minutes + ":" + sec;
    }
}
