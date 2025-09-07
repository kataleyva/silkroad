import java.util.ArrayList;
import java.util.List;

public class Posicion {

    public static int[][] generateSpiral(int size) {
        List<int[]> spiral = new ArrayList<>();

        // Comenzamos en el centro (0, 0)
        int x = 0, y = 0;
        spiral.add(new int[]{x, y});

        // Direcciones: derecha(+y), abajo(-x), izquierda(-y), arriba(+x)
        int[] dx = {0, -80, 0, 80};  // cambios en x
        int[] dy = {80, 0, -80, 0};  // cambios en y

        int direction = 0;  // empezamos moviéndonos a la derecha (+y)
        int steps = 1;      // número de pasos a dar en la dirección actual

        while (spiral.size() < size) {
            // Hacer pasos en la dirección actual (hacemos esto dos veces por nivel)
            for (int i = 0; i < 2 && spiral.size() < size; i++) {
                for (int j = 0; j < steps && spiral.size() < size; j++) {
                    x += dx[direction];
                    y += dy[direction];
                    spiral.add(new int[]{x, y});
                }
                direction = (direction + 1) % 4;  // cambiar dirección
            }
            steps++;  // incrementar el número de pasos para el siguiente nivel
        }

        // Convertir List a array
        return spiral.toArray(new int[spiral.size()][]);
    }
}