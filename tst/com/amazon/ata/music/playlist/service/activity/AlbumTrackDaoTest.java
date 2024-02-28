package com.amazon.ata.music.playlist.service.activity;

import com.amazon.ata.music.playlist.service.dynamodb.AlbumTrackDao;
import com.amazon.ata.music.playlist.service.dynamodb.models.AlbumTrack;
import com.amazon.ata.music.playlist.service.exceptions.AlbumTrackNotFoundException;
import com.amazon.ata.music.playlist.service.helpers.AlbumTrackTestHelper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class AlbumTrackDaoTest {
    @Mock
    private DynamoDBMapper dynamoDBMapper;
    @InjectMocks
    private AlbumTrackDao albumTrackDao;

    private AlbumTrack expectedAlbumTrack;

    //AlbumTrackTestHelper needs a sequence number to generate an AlbumTrack
    private final int RANDOM_SEQUENCE_NUMBER = 808;

    @BeforeEach
    void setUp() {
        initMocks(this);
        expectedAlbumTrack = AlbumTrackTestHelper.generateAlbumTrack(RANDOM_SEQUENCE_NUMBER);
    }

    @Test
    public void getAlbumTrackFromDynamoDB_returnExpectedAlbumTrack() {
        //GIVEN
        when(dynamoDBMapper.load(AlbumTrack.class, expectedAlbumTrack.getAsin())).thenReturn(expectedAlbumTrack);
        //WHEN
        AlbumTrack actualAlbumTrack = albumTrackDao.getAlbumTrack(expectedAlbumTrack.getAsin(), expectedAlbumTrack.getTrackNumber());
        //THEN
        assertEquals(expectedAlbumTrack, actualAlbumTrack);
    }

    @Test
    public void getAlbumTrackFromDynamoDB_returnsNull_throwsAlbumTrackNotFoundException() {
        //GIVEN
        when(dynamoDBMapper.load(AlbumTrack.class, RANDOM_SEQUENCE_NUMBER)).thenReturn(null);
        //WHEN
        //THEN
        assertThrows(AlbumTrackNotFoundException.class, () -> {
            albumTrackDao.getAlbumTrack(Integer.toString(RANDOM_SEQUENCE_NUMBER), 0);
        });
    }

}
