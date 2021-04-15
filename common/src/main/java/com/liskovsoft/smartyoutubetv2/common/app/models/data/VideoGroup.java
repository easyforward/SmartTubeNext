package com.liskovsoft.smartyoutubetv2.common.app.models.data;

import com.liskovsoft.mediaserviceinterfaces.data.MediaGroup;
import com.liskovsoft.mediaserviceinterfaces.data.MediaItem;
import com.liskovsoft.sharedutils.mylogger.Log;

import java.util.ArrayList;
import java.util.List;

public class VideoGroup {
    private static final String TAG = VideoGroup.class.getSimpleName();
    private int mId;
    private String mTitle;
    private List<Video> mVideos;
    private MediaGroup mMediaGroup;
    private Category mCategory;
    private int mPosition;

    public static VideoGroup from(Category category) {
        return from(null, category);
    }

    public static VideoGroup from(MediaGroup mediaGroup) {
        return from(mediaGroup, null);
    }

    public static VideoGroup from(MediaGroup mediaGroup, Category category) {
        return from(mediaGroup, category, 0);
    }

    public static VideoGroup from(MediaGroup mediaGroup, Category category, int groupPosition) {
        VideoGroup videoGroup = new VideoGroup();
        videoGroup.mCategory = category;
        videoGroup.mPosition = groupPosition;

        if (mediaGroup == null) {
            return videoGroup;
        }

        videoGroup.mMediaGroup = mediaGroup;
        videoGroup.mTitle = mediaGroup.getTitle();
        // TODO: replace with real id
        videoGroup.mId = mediaGroup.hashCode();
        videoGroup.mVideos = new ArrayList<>();

        if (mediaGroup.getMediaItems() == null) {
            Log.e(TAG, "MediaGroup doesn't contain media items. Title: " + mediaGroup.getTitle());
            return videoGroup;
        }

        for (MediaItem item : mediaGroup.getMediaItems()) {
            Video video = Video.from(item);
            // Group position in multi-grid fragments
            video.groupPosition = videoGroup.mPosition;
            videoGroup.mVideos.add(video);
        }

        return videoGroup;
    }

    public List<Video> getVideos() {
        return mVideos;
    }

    public String getTitle() {
        return mTitle;
    }

    public int getId() {
        return mId;
    }

    public MediaGroup getMediaGroup() {
        return mMediaGroup;
    }

    public Category getCategory() {
        return mCategory;
    }

    public boolean isEmpty() {
        return mVideos == null;
    }

    /**
     * Group position in multi-grid fragments<br/>
     * It isn't used on other types of fragments.
     */
    public int getPosition() {
        return mPosition;
    }
}
