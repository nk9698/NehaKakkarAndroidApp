package com.knavic.nehakakkar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    private String[] videoIds;
    private Lifecycle lifecycle;

    public VideoAdapter() {
    }

    public VideoAdapter(String[] videoIds, Lifecycle lifecycle) {
        this.videoIds = videoIds;
        this.lifecycle = lifecycle;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent, false);
        lifecycle.addObserver(youTubePlayerView);

        return new VideoViewHolder(youTubePlayerView);

    }

    @Override
    public void onBindViewHolder(final VideoViewHolder holder, int position) {

        holder.cueVideo(videoIds[position]);
        holder.fsswitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.fsswitch.isChecked()){
                    holder.youTubePlayerView.enterFullScreen();

                }else {
                    holder.youTubePlayerView.exitFullScreen();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoIds.length;
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder{

        private YouTubePlayerView youTubePlayerView;
        private YouTubePlayer youTubePlayer;
        private String currentVideoId;
        private Switch fsswitch;


        VideoViewHolder(YouTubePlayerView playerView) {
            super(playerView);
            fsswitch =(Switch)playerView.findViewById(R.id.switch1);
            youTubePlayerView = playerView;

            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer initializedYouTubePlayer) {
                    youTubePlayer = initializedYouTubePlayer;
                    youTubePlayer.cueVideo(currentVideoId, 0);
                }
            });
        }

        void cueVideo(String videoId) {
            currentVideoId = videoId;

            if(youTubePlayer == null)
                return;

            youTubePlayer.cueVideo(videoId, 0);
        }
    }
}
