package corridor;

class Format {
    final int headerSize;
    final String[] tableHeaders;
    Format(int headerSize, String[] tableHeaders) {
        this.headerSize = headerSize;
        this.tableHeaders = tableHeaders;
    }
    @Override
    public String toString() {
        StringBuilder sb =  new StringBuilder()
                .append("Header size: ").append(headerSize)
                .append("\n");
        for (String s: tableHeaders) {
            sb.append(s).append(" ");
        }
        return sb.toString();
    }
}
