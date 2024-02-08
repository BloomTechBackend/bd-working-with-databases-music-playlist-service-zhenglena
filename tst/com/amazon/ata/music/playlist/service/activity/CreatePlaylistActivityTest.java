package com.amazon.ata.music.playlist.service.activity;

import com.amazon.ata.music.playlist.service.dynamodb.PlaylistDao;
import com.amazon.ata.music.playlist.service.models.requests.CreatePlaylistRequest;
import org.mockito.InjectMocks;
import org.mockito.Mock;


public class CreatePlaylistActivityTest {
    @Mock
    private PlaylistDao playlistDao;
    @InjectMocks
    private CreatePlaylistActivity createPlaylistActivity;



}
