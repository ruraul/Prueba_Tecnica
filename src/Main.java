import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String token = "h8JLQvfj5Yl1iQeOvBT43d17RoDBO6UQ";
        String url = "https://elastic.snaplogic.com/api/1/rest/slsched/feed/Partners/AllDatum/Entrevista_Integracion/LeeArchivoNombresTask?archivo=first_names&extension=txt";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .header("Authorization", "Bearer " + token)
                .build();
        try{
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()){
                String responseBody = response.body().string();
                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNode = mapper.readTree(responseBody);
                System.out.println("Resultado ejercicio 1: " + muestraValor(jsonNode));
                System.out.println("Resultado ejercicio 2: " + adjacentNumber());
            }else{
                System.out.println("Error: " + response.code());
            }
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }
    public static int valorNumerico(String nombre, int position) {
        int resultado = 0;
        String alfabeto = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < nombre.length(); i++) {
            char c = nombre.charAt(i);
            int indice = alfabeto.indexOf(Character.toUpperCase(c));
            if (indice != -1) {
                resultado += indice + 1;
            }
        }
        return resultado * position;
    }

    public static int muestraValor(JsonNode nombres) {
        List<String> listaNombres = new ArrayList<>();
        nombres.forEach(n -> listaNombres.add(n.get("NAME").toString()));
        listaNombres.sort((o1, o2) -> o1.compareTo(o2));
        int valorTotal = 0;
        for (int i = 0; i < listaNombres.size(); i++) {
            String nombre = listaNombres.get(i);
            int valor = valorNumerico(nombre,i+1);
            valorTotal += valor;
        }
        return valorTotal;
    }

    public static long adjacentNumber(){
        String numero = "7316717653133062491922511967442657474235534919493496983520312774506326239578318016984801869478851843858615607891129494954595017379583319528532088055111254069874715852386305071569329096329522744304355766896648950445244523161731856403098711121722383113622298934233803081353362766142828064444866452387493035890729629049156044077239071381051585930796086670172427121883998797908792274921901699720888093776657273330010533678812202354218097512545405947522435258490771167055601360483958644670632441572215539753697817977846174064955149290862569321978468622482839722413756570560574902614079729686524145351004748216637048440319989000889524345065854122758866688116427171479924442928230863465674813919123162824586178664583591245665294765456828489128831426076900422421902267105562632111110937054421750694165896040807198403850962455444362981230987879927244284909188845801561660979191338754992005240636899125607176060588611646710940507754100225698315520005593572972571636269561882670428252483600823257530420752963450";
        int adj = 13;
        int size = numero.length();
        long[] info = new long[size];
        int inicio = 0;
        while (inicio < size - adj) {
            String line = numero.substring(inicio, inicio + adj);
            if (line.contains("0")) {
                inicio += line.indexOf("0") + 1;
            }else {
                if (inicio == 0 || info[inicio - 1] == 0) {
                    long product = 1;
                    for (char c : line.toCharArray()) {
                        product *= Character.getNumericValue(c);
                    }
                    info[inicio] = product;
                } else {
                    info[inicio] = info[inicio - 1] / Character.getNumericValue(numero.charAt(inicio - 1)) * Character.getNumericValue(line.charAt(line.length() - 1));
                }
                inicio++;
            }
        }
        int index = 0;
        long maxProduct = 0;
        for (int i = 0; i < info.length; i++) {
            if (info[i] > maxProduct) {
                maxProduct = info[i];
                index = i;
            }
        }
        return maxProduct;
    }

}