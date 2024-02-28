package com.amazon.ata.music.playlist.service.converters;

import com.amazon.ata.music.playlist.service.dynamodb.models.AlbumTrack;
import com.amazon.ata.music.playlist.service.models.PlaylistModel;
import com.amazon.ata.music.playlist.service.dynamodb.models.Playlist;
import com.amazon.ata.music.playlist.service.models.SongModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModelConverter {
    /**
     * Converts a provided {@link Playlist} into a {@link PlaylistModel} representation.
     * @param playlist the playlist to convert
     * @return the converted playlist
     */
    public PlaylistModel toPlaylistModel(Playlist playlist) {
        //if the playlist.getTags() is null, we want to pass in null for the table. if not, convert to a List
        List<String> tagsList = null;
        if (playlist.getTags() != null) {
            tagsList = new ArrayList<>(playlist.getTags());
        }
        return PlaylistModel.builder()
                .withId(playlist.getId())
                .withName(playlist.getName())
                .withCustomerId(playlist.getCustomerId())
                .withSongCount(playlist.getSongCount())
                .withTags(tagsList)
                .build();
    }

    /**
     * Coverts a provided {@link AlbumTrack} into a {@link SongModel} representation.
     * @param albumTrack the album track to convert
     * @return the converted song
     */
    public SongModel toSongModel(AlbumTrack albumTrack) {
        return SongModel.builder()
                .withAlbum(albumTrack.getAlbumName())
                .withAsin(albumTrack.getAsin())
                .withTitle(albumTrack.getSongTitle())
                .withTrackNumber(albumTrack.getTrackNumber())
                .build();
    }

    /**
     * Converts a provided {@link AlbumTrack} list into a {@link SongModel} list.
     * <p>
     * If the provided AlbumTrack list is empty, it will return an empty SongModel list.
     * <p>
     * @param albumTracksList the album track list to convert
     * @return the converted song model list
     */
    public List<SongModel> toSongModelList(List<AlbumTrack> albumTracksList) {
        List<SongModel> songList = new ArrayList<>();
        if (!albumTracksList.isEmpty()) {
            for (AlbumTrack track : albumTracksList) {
                songList.add(toSongModel(track));
            }
        }

        return songList;
    }
}
