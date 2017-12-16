package corridor;

class Format {
    final int headerSize;
    final boolean hasData;
    final String[] tableHeaders;
    Format(int headerSize, boolean hasData, String[] tableHeaders) {
        this.headerSize = headerSize;
        this.hasData = hasData;
        this.tableHeaders = tableHeaders;
    }
    @Override
    public String toString() {
        StringBuilder sb =  new StringBuilder()
                .append("Header size: ").append(headerSize)
                .append(", has data: ").append(hasData)
                .append("\n");
        for (String s: tableHeaders) {
            sb.append(s).append(" ");
        }
        return sb.toString();
    }
}
