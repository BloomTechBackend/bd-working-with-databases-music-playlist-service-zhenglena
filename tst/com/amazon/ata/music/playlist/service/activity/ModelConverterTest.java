package com.amazon.ata.music.playlist.service.activity;

import com.amazon.ata.music.playlist.service.converters.ModelConverter;
import com.amazon.ata.music.playlist.service.dynamodb.AlbumTrackDao;
import com.amazon.ata.music.playlist.service.dynamodb.models.AlbumTrack;
import com.amazon.ata.music.playlist.service.dynamodb.models.Playlist;
import com.amazon.ata.music.playlist.service.helpers.AlbumTrackTestHelper;
import com.amazon.ata.music.playlist.service.helpers.PlaylistTestHelper;
import com.amazon.ata.music.playlist.service.models.PlaylistModel;
import com.amazon.ata.music.playlist.service.models.SongModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModelConverterTest {
    private ModelConverter modelConverter;
    private Playlist playlist;
    private AlbumTrack albumTrack;
    private PlaylistModel expectedPlaylistModel;

    //random number used because AlbumTrackTestHelper needs it
    private final int RANDOM_SEQUENCE_NUMBER = 808;

    @BeforeEach
    void setUp() {
        modelConverter = new ModelConverter();
        playlist = PlaylistTestHelper.generatePlaylist();
        albumTrack = AlbumTrackTestHelper.generateAlbumTrack(RANDOM_SEQUENCE_NUMBER);
        expectedPlaylistModel = PlaylistModel.builder()
                .withId(playlist.getId())
                .withName(playlist.getName())
                .withCustomerId(playlist.getCustomerId())
                .withTags(new ArrayList<>(playlist.getTags()))
                .withSongCount(playlist.getSongCount())
                .build();
    }

    @Test
    public void toPlaylistModel_validPlaylist_returnPlaylistModel() {
        //GIVEN
        //WHEN
        PlaylistModel actualPlaylistModel = modelConverter.toPlaylistModel(playlist);
        //THEN
        assertEquals(expectedPlaylistModel.getId(), actualPlaylistModel.getId());
        assertEquals(expectedPlaylistModel.getName(), actualPlaylistModel.getName());
        assertEquals(expectedPlaylistModel.getTags(), actualPlaylistModel.getTags());
        assertEquals(expectedPlaylistModel.getSongCount(), actualPlaylistModel.getSongCount());
        assertEquals(expectedPlaylistModel.getCustomerId(), actualPlaylistModel.getCustomerId());
    }

    @Test
    public void toSongModel_validAlbumTrack_returnSongModel() {
        //GIVEN
        //WHEN
        SongModel actualSongModel = modelConverter.toSongModel(albumTrack);
        //THEN
        AlbumTrackTestHelper.assertAlbumTrackEqualsSongModel(albumTrack, actualSongModel);
    }
}
