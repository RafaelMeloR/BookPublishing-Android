package com.example.bookpublishingproject.database;

public class PublishingDbSchema {

    public static final class PublisherTable{
        public static final String NAME = "publisher";

        public static final class Cols {
            public static final String P_ID="p_id";
            public static final String P_NAME="p_name";
            public static final String P_ADDRESS="p_address";

        }
    }

    public static final class BookTable{
        public static final String NAME = "book";

        public static final class Cols {
            public static final String B_ID="b_id";
            public static final String B_AUTHOR="b_author";
            public static final String B_TITLE="b_title";
            public static final String B_ISBN="b_isbn";
            public static final String B_TYPE="b_type";
            public static final String B_PRICE="b_price";
            public static final String P_ID = "p_id"; // Foreign key referencing P_ID in PublisherTable

        }
    }

    public static final class chapterTable{
        public static final String NAME = "chapter";

        public static final class Cols {
            public static final String B_ID="b_id";
            public static final String C_NO="c_no";
            public static final String C_TITLE="c_title";
            public static final String C_PRICE="c_price";

        }
    }

}
