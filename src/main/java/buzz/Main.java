package buzz;

import buzz.view.GUI;

public class Main {
    public static void main(String[] args) {
        // Această metodă apelează main-ul din GUI,
        // evitând eroarea "JavaFX runtime components are missing"
        GUI.main(args);
    }
}