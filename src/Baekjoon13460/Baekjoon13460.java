package Baekjoon13460;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 *  빨간 구슬  R , 파란 구슬 N , 장애물 # , 구멍의 위치 O
 *  1. 빨간 구슬이 구멍에 빠지면 성공, 파란 구슬이 구멍에 빠지면 실패
 *  2. 왼쪽으로 기울이기, 오른쪽으로 기울이기, 위쪽으로 기울이기, 아래쪽으로 기울이기
 *  3. 10번이하로 움직여서 빨간 구슬 빼낼 수 없으면 -1 을 출력
 */

class Marble {
    int rX;
    int rY;
    int bX;
    int bY;
    int cnt;
    Marble(int rX, int rY, int bX, int bY, int cnt){
        this.rX = rX;
        this.rY = rY;
        this.bX = bX;
        this.bY = bY;
        this.cnt = cnt;
    }
}
public class Baekjoon13460 {

    static int N, M;
    static char map[][];
    static boolean visited[][][][];
    static int answerX,answerY;
    static Marble red,blue;

    static int answerCnt=0;
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {-1, 0, 1, 0};
// 4 ,3 , 2, 1
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new char[N][M];
        visited = new boolean[N][M][N][M];
        for(int i=0; i<N; i++){
            String str = br.readLine();
            for(int j=0; j<M; j++){
                map[i][j] = str.charAt(j);
                if(map[i][j]=='O') {
                    answerX = i;
                    answerY = j;
                }else if(map[i][j]=='R'){
                    red = new Marble(i, j, 0, 0, 0);
                } else if(map[i][j]=='B'){
                    blue = new Marble(0, 0, i, j, 0);
                }
            }
        }

        System.out.println(answer());
    }

    static int answer(){
        Queue<Marble> queue = new LinkedList<>();
        queue.add(new Marble(red.rX, red.rY, blue.bX, blue.bY, 1));

        while(!queue.isEmpty()){
            Marble marble = queue.poll();
            int currentRx = marble.rX;
            int currentRy = marble.rY;
            int currentBx = marble.bX;
            int currentBy = marble.bY;
            int curCnt = marble.cnt;
            if(answerCnt > 10) {
                return -1;
            }

            for(int i=0; i<4; i++){
                int newRx = currentRx;
                int newRy = currentRy;
                int newBx = currentBx;
                int newBy = currentBy;

                boolean isRedHole = false;
                boolean isBlueHole = false;

                while(map[newRx+dx[i]][newRy+dy[i]]!='#'){
                    newRx +=dx[i];
                    newRy +=dy[i];
                    if(newRx==answerX && newRy==answerY){
                        isRedHole = true;
                        break;
                    }
                }

                while(map[newBx+dx[i]][newBy+dy[i]]!='#'){
                    newBx += dx[i];
                    newBy += dy[i];
                    if(newBx==answerX && newBy==answerY){
                       isBlueHole = true;
                       break;
                    }
                }

                if(isRedHole){
                    return curCnt;
                }

                if(isBlueHole){
                    continue;
                }
                if(newRx==newBx && newRy==newBy){
                    if (i == 0) { // 왼쪽으로 기울이기
                        if(currentRy > currentBy) newRy -= dy[i];
                        else newBy -= dy[i];
                    } else if(i==1){ // 위로 기울이기
                        if(currentRx>currentBx) newRx -= dx[i];
                        else newBx -= dx[i];
                    } else if (i == 2) { // 오른쪽으로 기울이기
                        if(currentRy<currentBy) newRy -= dy[i];
                        else newBy -= dy[i];
                    } else {
                        if(currentRx<currentBy) newRx -= dx[i];
                        else newBx -= dx[i];
                    }
                }

                if(!visited[newRx][newRy][newBx][newBy]){
                    visited[newRx][newRy][newBx][newBy] = true;
                    queue.add(new Marble(newRx, newRy, newBx, newBy, curCnt + 1));
                }
            }
        }

        return -1;
    }
}
