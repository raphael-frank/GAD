package gad.doublehashing;

public final class Main {
    private Main() {

    }

    public static void main(String[] args) {
        DoubleHashTable<String, String> table = new DoubleHashTable<>(17, new StringHashableFactory());

        table.insert("hallo wie geht es", "Hallo");
        table.insert("gut", "Welt");
        table.insert("hallo wie geht es", "Hallo2");

        System.out.println(table.find("gt"));
    }
}
