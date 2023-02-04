package litvinova.mergesort;
public class MergeSort {

    public static void main(String[] args) throws NoRequiredParamException {
        int k = 0;
        int sortMode = 0;   // 0 - по возрастанию, 1 - по убыванию
        if(args.length == 0) {
            throw new NoRequiredParamException("-i : сортировка чисел, -s : сортировка строк\n\t" +
                    "Для выполнения сортировки необходим выходной файл и как минимум один входной");
        }
        if (args[k].equals("-d")) {
            sortMode = 1;
            k++;
        } else if (args[k].equals("-a")) {
            k++;
        }
        if (args[k].equals("-i")) {
            k++;
            MergeSortInt.Sort(args, k, sortMode);
        } else if (args[k].equals("-s")) {
            k++;
            MergeSortString.Sort(args, k, sortMode);

        } else {
            throw new NoRequiredParamException("-i : сортировка чисел, -s : сортировка строк");
        }
    }
}
