package asiantech.internship.summer.recycler_view.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import asiantech.internship.summer.R;

public class TimelineCreator {
    private static final int TIMELINE_NUMBER = 10;
    private static final String[] DESCRIPTIONS = new String[]{
            "Vai l \nKinh qua kinh",
            "Dep vlllll \nTha tim",
            "QuAAAAAAAAAA\nQuAAAAAAAAAAAAAAA",
            "Hay lam \nMa thuong thoi",
            "LOOOOOOOOOOL\nLOOOOOOOOOOOL\nLOOOOOOL",
            "........\n.."
    };
    private static final int[] TIMELINE_IMAGES_IDS = new int[]{
            R.drawable.img_status_1, R.drawable.img_status_2, R.drawable.img_status_3,
            R.drawable.img_status_4, R.drawable.img_status_5, R.drawable.img_status_6,
            R.drawable.img_status_7
    };

    public static List<Timeline> createListTimeline() {
        List<Timeline> result = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < TIMELINE_NUMBER; i++) {
            String description = DESCRIPTIONS[random.nextInt(DESCRIPTIONS.length)];
            int timelineImageId = TIMELINE_IMAGES_IDS[random.nextInt(TIMELINE_IMAGES_IDS.length)];
            Author author = AuthorCreator.createAuthor();
            Timeline timeLine = new Timeline(author, description, timelineImageId);
            result.add(timeLine);
        }
        return result;
    }
}
