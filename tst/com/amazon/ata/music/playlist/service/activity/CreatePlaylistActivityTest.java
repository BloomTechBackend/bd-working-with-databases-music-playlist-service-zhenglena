package com.amazon.ata.music.playlist.service.activity;

import com.amazon.ata.music.playlist.service.dynamodb.PlaylistDao;
import com.amazon.ata.music.playlist.service.dynamodb.models.Playlist;
import com.amazon.ata.music.playlist.service.models.requests.CreatePlaylistRequest;
import com.amazon.ata.music.playlist.service.models.results.CreatePlaylistResult;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;


public class CreatePlaylistActivityTest {
    @Mock
    private PlaylistDao playlistDao;
    @InjectMocks
    private CreatePlaylistActivity createPlaylistActivity;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    public void handleRequest_createdPlaylist_returnsPlaylistRequest() {
        // GIVEN
        String expectedId = "expectedId";
        String expectedName = "expectedName";
        String expectedCustomerId = "expectedCustomerId";
        List<String> expectedTags = new ArrayList<>();
        expectedTags.add("tag");

        Playlist playlist = new Playlist();
        playlist.setName(expectedName);
        playlist.setCustomerId(expectedCustomerId);
        playlist.setSongCount(0);
        playlist.setTags(Sets.newHashSet(expectedTags));

        CreatePlaylistRequest request = CreatePlaylistRequest.builder()
                .withCustomerId(expectedCustomerId)
                .withName(expectedName)
                .withTags(expectedTags)
                .build();

        // WHEN
        CreatePlaylistResult result = createPlaylistActivity.handleRequest(request, null);

        // THEN
        assertEquals(expectedName, result.getPlaylist().getName());
        assertEquals(expectedCustomerId, result.getPlaylist().getCustomerId());
        assertEquals(expectedTags, result.getPlaylist().getTags());

    }
}
