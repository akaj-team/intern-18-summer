package asiantech.internship.summer.service_and_broadcast_receiver.model;

import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.R;

public class Song {
    private String Title;
    private int File;
    private int AvatarSinger;
    private String Singer;

    public Song(String title, int file, int avatarSinger, String singer) {
        Title = title;
        File = file;
        AvatarSinger = avatarSinger;
        Singer = singer;
    }

    public String getTitle() {
        return Title;
    }

    public int getFile() {
        return File;
    }

    public int getAvatarSinger() {
        return AvatarSinger;
    }

    public String getSinger() {
        return Singer;
    }

    public static List<Song> getListSong() {
        List<Song> listSongs = new ArrayList<>();
        listSongs.add(new Song("Đừng như thói quen", R.raw.dung_nhu_thoi_quen, R.drawable.img_avt_jaykii_and_sara, "Jaykii and Sara"));
        listSongs.add(new Song("Cuộc sống em ổn không", R.raw.cuoc_song_em_on_khong, R.drawable.img_avt_anh_tu, "Anh Tú"));
        listSongs.add(new Song("Đừng quên tên anh", R.raw.dung_quen_ten_anh, R.drawable.img_avt_hoa_vinh, "Hoa Vinh"));
        listSongs.add(new Song("Lỡ thương một người", R.raw.lo_thuong_mot_nguoi, R.drawable.img_avt_nguyen_dinh_vu, "Nguyễn Đình Vũ"));
        listSongs.add(new Song("Rồi người thương cũng hóa người dưng", R.raw.roi_nguoi_thuong_cung_hoa_nguoi_dung, R.drawable.img_avt_hien_ho, "Hiền Hồ"));
        listSongs.add(new Song("Sai người sai thời điểm", R.raw.sai_nguoi_sai_thoi_diem, R.drawable.img_avt_thanh_hung, "Thanh Hưng"));
        return listSongs;
    }
}
