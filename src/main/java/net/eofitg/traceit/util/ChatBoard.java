package net.eofitg.traceit.util;

import net.eofitg.traceit.TraceIt;

import java.util.ArrayDeque;
import java.util.Queue;

public class ChatBoard {

    public static Queue<String> board = new ArrayDeque<>();

    public static void addMessage(String msg) {
        if (board.size() != TraceIt.config.size) {
            board.offer(msg);
        } else {
            board.poll();
            board.offer(msg);
        }
    }

}
