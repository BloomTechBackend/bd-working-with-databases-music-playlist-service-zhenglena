package com.amazon.ata.music.playlist.service.activity;

import com.amazon.ata.music.playlist.service.dynamodb.PlaylistDao;
import com.amazon.ata.music.playlist.service.dynamodb.models.Playlist;
import com.amazon.ata.music.playlist.service.exceptions.PlaylistNotFoundException;
import com.amazon.ata.music.playlist.service.helpers.PlaylistTestHelper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PlaylistDaoTest {
    @Mock
    private DynamoDBMapper dynamoDBMapper;
    @InjectMocks
    private PlaylistDao playlistDao;

    private Playlist expectedPlaylist;
    private String playlistId;
    private Set<String> listOfTags;



    @BeforeEach
    void setUp() {
        initMocks(this);

        expectedPlaylist = PlaylistTestHelper.generatePlaylist();
        playlistId = expectedPlaylist.getId();
    }

    @Test
    public void getPlaylist_returnPlaylist() {
        //GIVEN
        //WHEN
        when(dynamoDBMapper.load(Playlist.class, playlistId)).thenReturn(expectedPlaylist);
        Playlist actualPlaylist = playlistDao.getPlaylist(playlistId);

        //THEN
        assertEquals(expectedPlaylist.getId(), actualPlaylist.getId());
        assertEquals(expectedPlaylist.getName(), actualPlaylist.getName());
        assertEquals(expectedPlaylist.getTags(), actualPlaylist.getTags());
        assertEquals(expectedPlaylist.getSongCount(), actualPlaylist.getSongCount());
        assertEquals(expectedPlaylist.getCustomerId(), actualPlaylist.getCustomerId());
    }

    @Test
    public void getPlaylist_nullPlaylist_throwsPlaylistNotFoundException() {
        //GIVEN
        //WHEN
        when(dynamoDBMapper.load(Playlist.class, playlistId)).thenReturn(null);

        //THEN
        assertThrows(PlaylistNotFoundException.class, () -> {
            playlistDao.getPlaylist(playlistId);
        });
    }

}
