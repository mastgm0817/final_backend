//package final_backend.Board.service;
//
//import final_backend.Board.model.Board;
//import final_backend.Board.model.CursorResult;
//import final_backend.Board.respository.BoardRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//class BoardServiceImplTest {
//
//    @InjectMocks
//    private BoardServiceImpl boardService;
//
//    @Mock
//    private BoardRepository boardRepository;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void getAllBoards() {
//        Board board1 = new Board();
//        Board board2 = new Board();
//
//        when(boardRepository.findAll(any())).thenReturn(Arrays.asList(board1, board2));
//
//        List<Board> boards = boardService.getAllBoards();
//
//        assertNotNull(boards);
//        assertEquals(2, boards.size());
//    }
//
//    @Test
//    void getBoardById() {
//        Long boardId = 1L;
//        Board board = new Board();
//        board.setBid(boardId);
//
//        when(boardRepository.findById(boardId)).thenReturn(Optional.of(board));
//
//        Optional<Board> resultBoard = boardService.getBoardById(boardId);
//
//        assertTrue(resultBoard.isPresent());
//        assertEquals(boardId, resultBoard.get().getBid());
//    }
//
//    @Test
//    void createBoard() {
//        Board board = new Board();
//
//        when(boardRepository.save(any(Board.class))).thenAnswer(i -> {
//            Board inputBoard = (Board) i.getArguments()[0];
//            inputBoard.setBid(1L);
//            return inputBoard;
//        });
//
//        Board savedBoard = boardService.createBoard(board);
//
//        assertNotNull(savedBoard);
//        assertEquals(1L, savedBoard.getBid());
//    }
//
//    @Test
//    void deleteBoard() {
//        Long boardId = 1L;
//
//        boardService.deleteBoard(boardId);
//
//        verify(boardRepository, times(1)).deleteById(boardId);
//    }
//
//    @Test
//    void updateBoard() {
//        Long boardId = 1L;
//        Board existingBoard = new Board();
//        existingBoard.setBid(boardId);
//        existingBoard.setBTitle("Old Title");
//
//        Board updatedBoard = new Board();
//        updatedBoard.setBTitle("New Title");
//
//        when(boardRepository.findById(boardId)).thenReturn(Optional.of(existingBoard));
//        when(boardRepository.save(any(Board.class))).thenAnswer(i -> i.getArguments()[0]);
//
//        Board resultBoard = boardService.updateBoard(boardId, updatedBoard);
//
//        assertNotNull(resultBoard);
//        assertEquals("New Title", resultBoard.getBTitle());
//    }
//
//    @Test
//    void getBoard() {
//        Long cursorId = 1L;
//        Pageable page = PageRequest.of(0, 10);
//        String findStr = "sample";
//        String findingMethod = "title";
//
//        Board board1 = new Board();
//        board1.setBid(1L);
//        Board board2 = new Board();
//        board2.setBid(2L);
//
//        when(boardRepository.findBybTitleContainingAndBidLessThanOrderByBidDesc(anyString(), anyLong(), any(Pageable.class)))
//                .thenReturn(Arrays.asList(board1, board2));
//
//        CursorResult<Board> result = boardService.getBoard(cursorId, page, findStr, findingMethod);
//
//        assertNotNull(result);
//                assertTrue(result.getValues().size() > 0);
//    }
//
//    @Test
//    void getBoardList_byTitle() {
//        Long cursorId = 1L;
//        Pageable page = PageRequest.of(0, 10);
//        String findStr = "sample";
//        String findingMethod = "title";
//
//        Board board1 = new Board();
//        board1.setBid(1L);
//        Board board2 = new Board();
//        board2.setBid(2L);
//
//        when(boardRepository.findBybTitleContainingAndBidLessThanOrderByBidDesc(anyString(), anyLong(), any(Pageable.class)))
//                .thenReturn(Arrays.asList(board1, board2));
//
//        List<Board> boards = boardService.getBoardList(cursorId, page, findStr, findingMethod);
//
//        assertNotNull(boards);
//        assertEquals(2, boards.size());
//    }
//
//    @Test
//    void increaseViewCount() {
//        Board board = new Board();
//        board.setBid(1L);
//        board.setB_views(1L);
//
//        when(boardRepository.save(any(Board.class))).thenReturn(board);
//
//        Board updatedBoard = boardService.increaseViewCount(board);
//
//        assertNotNull(updatedBoard);
//        assertEquals(2L, updatedBoard.getB_views());
//    }
//
//    @Test
//    void recommendIncrease() {
//        Long boardId = 1L;
//        Board board = new Board();
//        board.setBid(boardId);
//        board.setB_recommendations(1L);
//
//        when(boardRepository.findById(boardId)).thenReturn(Optional.of(board));
//        when(boardRepository.save(any(Board.class))).thenReturn(board);
//
//        Board resultBoard = boardService.recommendIncrease(boardId);
//
//        assertNotNull(resultBoard);
//        assertEquals(2L, resultBoard.getB_recommendations());
//    }
//
//    @Test
//    void hasNext_true() {
//        Long boardId = 1L;
//        when(boardRepository.existsByBidLessThan(boardId)).thenReturn(true);
//
//        Boolean result = boardService.hasNext(boardId);
//
//        assertTrue(result);
//    }
//
//    @Test
//    void hasNext_false() {
//        Long boardId = 1L;
//        when(boardRepository.existsByBidLessThan(boardId)).thenReturn(false);
//
//        Boolean result = boardService.hasNext(boardId);
//
//        assertFalse(result);
//    }
//
//
//
//}
