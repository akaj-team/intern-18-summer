package asiantech.internship.summer.broadcastandservice;

public final class getDurationTransfer {
    public static String getDuration(int duration) {
        int hours = duration / 3600000;
        int temps = duration - hours * 3600000;
//
//        int second = milisecond /1000;
        int minutes = temps / 60000;
        int second = (temps - minutes * 60000) / 1000;
        if (hours == 0) {
            return minutes + ":" + second;
        } else {
            return hours + ":" + minutes + ":" + second;
        }

    }
}
