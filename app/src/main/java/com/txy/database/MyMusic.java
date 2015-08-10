package com.txy.database;

import android.graphics.Bitmap;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "myMusic")
public class MyMusic extends Model{

    @Column
    private int mode;

    @Column
    private String path;

    @Column
    private String artist;

    @Column
    private String album;

    @Column
	private String title;

    @Column
	private long duration ;

	private Bitmap albumCover ;

    public MyMusic(){
        super();
    }

	public MyMusic(String title, String path, String artist, String album , long duration) {
		super();
		this.title = title;
		this.path = path;
		this.artist = artist;
		this.album = album;
		this.duration = duration ;
	}
	public MyMusic(String title, String path, String artist, String album , long duration , Bitmap albumCover) {
		super();
		this.title = title;
		this.path = path;
		this.artist = artist;
		this.album = album;
		this.duration = duration ;
		this.albumCover = albumCover ;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}
	
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	
	public Bitmap getAlbumCover() {
		return albumCover;
	} 
	@Override
	public String toString() {
		return "MyAudio [title=" + title + ", path=" + path + ", artist="
				+ artist + ", album=" + album + ", duration=" + duration
				+ ", albumCover=" + albumCover + "]";
	}
    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
	
}
