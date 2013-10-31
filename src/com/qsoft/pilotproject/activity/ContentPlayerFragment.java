package com.qsoft.pilotproject.activity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import com.qsoft.pilotproject.R;
import com.qsoft.pilotproject.utils.Utilities;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

/**
 * User: BinkaA
 * Date: 10/17/13
 * Time: 11:24 PM
 */
public class ContentPlayerFragment extends Fragment
{

    public static final String MEDIA_PATH = "/sdcard/";
    SeekBar.OnSeekBarChangeListener seekBarSongListener = new SeekBar.OnSeekBarChangeListener()
    {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b)
        {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar)
        {
            handler.removeCallbacks(updateTimeTask);
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar)
        {
            handler.removeCallbacks(updateTimeTask);
            int totalDuration = mediaPlayer.getDuration();
            int currentPosition = Utilities.progressToTimer(seekBar.getProgress(), totalDuration);
            mediaPlayer.seekTo(currentPosition);
            updateProgressBar();
        }

    };
    View.OnClickListener btPlayOnclickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            if (mediaPlayer.isPlaying())
            {
                if (mediaPlayer != null)
                {
                    mediaPlayer.pause();
                    btPlay.setImageResource(R.drawable.content_button_play);
                }
            }
            else
            {
                if (mediaPlayer != null)
                {
                    mediaPlayer.start();
                    btPlay.setImageResource(R.drawable.content_button_pause);
                }
            }
        }
    };
    private MediaPlayer mediaPlayer;
    private SeekBar songProgressBar;
    private Handler handler = new Handler();
    private SeekBar volumeProgressBar;
    private ImageButton btPlay;
    AudioManager  audioManager = null;
    private TextView tvTotalDuration;
    private TextView tvCurrentDuration;
    private Runnable updateTimeTask = new Runnable()
    {
        @Override
        public void run()
        {
            if (mediaPlayer != null)
            {
                long totalDuration = mediaPlayer.getDuration();
                long currentDuration = mediaPlayer.getCurrentPosition();
                tvTotalDuration.setText("" + Utilities.milliSecondsToTimer(totalDuration));
                tvCurrentDuration.setText("" + Utilities.milliSecondsToTimer(currentDuration));
                int progress = Utilities.getProgressPercentage(currentDuration, totalDuration);
                songProgressBar.setProgress(progress);
                handler.postDelayed(this, 100);
            }

        }
    };

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.program_content_player, container, false);
        mediaPlayer = new MediaPlayer();
        songProgressBar = (SeekBar) view.findViewById(R.id.seekBarPlayer);
        volumeProgressBar = (SeekBar) view.findViewById(R.id.seekBarVolume);
        tvTotalDuration = (TextView) view.findViewById(R.id.tvTotalTime);
        tvCurrentDuration = (TextView) view.findViewById(R.id.tvTimeCurrent);
        btPlay = (ImageButton) view.findViewById(R.id.ibPlayer);
        btPlay.setOnClickListener(btPlayOnclickListener);

        songProgressBar.setOnSeekBarChangeListener(seekBarSongListener);
        playSong(getSong());
        this.getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);
        initVolumeControls();
        return view;
    }

    private void initVolumeControls()
    {
        audioManager = (AudioManager) this.getActivity().getSystemService(Context.AUDIO_SERVICE);
        volumeProgressBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        volumeProgressBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));

        volumeProgressBar.setOnSeekBarChangeListener(volumeProgressBarOnChangeListener);


    }
    SeekBar.OnSeekBarChangeListener volumeProgressBarOnChangeListener = new SeekBar.OnSeekBarChangeListener()
    {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean b)
        {
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar)
        {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar)
        {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    };

    @Override
    public void onDestroy()
    {
        handler.removeCallbacks(updateTimeTask);
        mediaPlayer.release();
        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public void playSong(String path)
    {
        try
        {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            mediaPlayer.start();
            btPlay.setImageResource(R.drawable.content_button_pause);
            songProgressBar.setProgress(0);
            songProgressBar.setProgress(100);
            updateProgressBar();
        }
        catch (IOException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private void updateProgressBar()
    {
        handler.postDelayed(updateTimeTask, 100);

    }

    public String getSong()
    {
        File home = new File(MEDIA_PATH);
        for (File file : home.listFiles(new FileExtensionFilter()))
        {
            return file.getPath();
        }
        return null;
    }

    class FileExtensionFilter implements FilenameFilter
    {
        public boolean accept(File dir, String name)
        {
            return (name.endsWith(".mp3") || name.endsWith(".MP3"));
        }
    }


}