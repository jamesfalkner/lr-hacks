import java.util.Arrays;
import java.util.List;

public class SocialDriverConstants {

public static final int WAITTIME = 10;

// a bunch of random first names used when creating users
public static final String[] FIRST_NAMES = new String[]{"Stacy",
    "Florinda",
    "Adolfo",
    "Eulalia",
    "Merrill",
    "Fay",
    "Simon",
    "Kena",
    "Estelle",
    "Delmy",
    "Edris",
    "Jeane",
    "Hope",
    "Ricky",
    "Ellan",
    "Crysta",
    "Shelton",
    "Jann",
    "Elise",
    "Rickey",
    "Joetta",
    "Sherman",
    "Edith",
    "Artie",
    "Troy",
    "Young",
    "Ligia",
    "Leon",
    "Gustavo",
    "Bobby",
    "Christene",
    "Taunya",
    "Lauri",
    "Ewa",
    "Yael",
    "Sylvester",
    "Angie",
    "Manuel",
    "Carlos",
    "Nada",
    "Marisela",
    "Eartha",
    "Kasandra",
    "Kit",
    "Cassidy",
    "Tia",
    "Marleen",
    "Joshua",
    "Dorotha",
    "Rosenda",};
// A bunch of random gravatar IDs for use when creating new users
public static final String[] GRAVATAR_IDS = new String[]{
    "205e460b479e2e5b48aec07710c08d50", "6ea52ba884713c11a9f814dde6867c97", "84987b436214f52ec0b04cd1f8a73c3c",
    "c63392ca320086522cf4d55cbf1d3808", "8f0c9789bd69a98b3103bcefa878f1bb", "86debe7ed7ece0f968097a768dcbd5cb",
    "4d346581a3340e32cf93703c9ce46bd4", "75fb365927cb3f5f7b677682d6249406", "3cb9afe63d364690c0e188fb16473277",
    "5b3b5ce04dd402124aba53142b3e47f6", "6cf147a5459184fdd93a2328d03ebcb4", "aac7e0386facd070f6d4b817c257a958",
    "1a33e7a69df4f675fcd799edca088ac2", "4f293e41d789d759831dc43550b7a9ff", "aa19dcc345fb0f1a6ddaf9b9863a678f",
    "f45143c409f3deed51a65a238654f7f4", "24038397a5d55c66d086e0a1721a5ade", "3751204a1a48ba86eb6bb83a96640318",
    "16aeda409d2a00b51ac4c5058a3a8435", "777ec1b95dbc49dd05d724da9bef1edd"
};
// A bunch of fake job titles used when creating new users
public static final String[] JOB_TITLES = new String[]{
    "Junior Personnel Trainee", "Deputy Marketing Consultant", "Regional Financial Clerk",
    "PlayStation Brand Ambassador", "Finish Carpenter",
    "Helpdesk Technician @ Pentagon", "Saltlick Cashier", "Breakfast Sandwich Maker",
    "Licensed Seamless Gutter Contractor", "Intra Systems Consultant"
};

// a bunch of random last names used when creating new users
public static final String[] LAST_NAMES = new String[]{"Lobato",
    "Dehne",
    "Boehman",
    "Soluri",
    "Sojka",
    "Morgan",
    "Rex",
    "Sirmans",
    "Junkins",
    "Bonneau",
    "Thoman",
    "Gouge",
    "Stambaugh",
    "Googe",
    "Newlin",
    "Stamand",
    "Feldman",
    "Gusman",
    "Kephart",
    "Blunk",
    "Overbay",
    "Selvage",
    "Perry",
    "Lampe",
    "Bedwell",
    "Olivero",
    "Paoletti",
    "Vernon",
    "Cogar",
    "Dellinger",
    "Eckhart",
    "Cosner",
    "Ohler",
    "Montenegro",
    "Dewar",
    "Heimann",
    "Tinoco",
    "Dunham",
    "Bayles",
    "Velarde",
    "Tatom",
    "Reader",
    "Morant",
    "Oiler",
    "Snavely",
    "Cambra",
    "Bryden",
    "Narcisse",
    "Bracero",
    "Marlett",
};

// hard-coded values for article ids for expertise finder web content
// articles.
public static final String ASSETLIST_ARTICLE_ID =
    "SOCIALDRIVER-ASSETLISTARTICLE";
public static final String EXPERTSLIST_ARTICLE_ID =
    "SOCIALDRIVER-EXPERTSARTICLE";
public static final String CLOUD_ARTICLE_ID = "SOCIALDRIVER-CLOUDARTICLE";
// standard list of common english words to eliminate from tags
public static final List<String> ENGLISH_STOP_WORDS = Arrays.asList(
    "a", "an", "and", "are", "as", "at", "be", "but", "by",
    "for", "if", "in", "into", "is", "it",
    "no", "not", "of", "on", "or", "such",
    "that", "the", "their", "then", "there", "these",
    "they", "this", "to", "was", "will", "with");

// how many times to query wikipedia, to get a good set of documents
public static final int WIKIPEDIA_FETCH_COUNT = 3;

// hard-coded set of tags to use when searching wikipedia
public static final String[] FAVORED_TAGS = new String[]{
    "java",
    "liferay",
    "javaee",
    "ldap",
    "portal",
    "content management system",
    "social networking",
    "mobile",
    "android",
    "cloud computing",
    "j2ee",
    "enterprise software",
    "sharepoint",
    "single sign on",
    "gadgets",
    "open source",
    "collaboration"
};

// max # of terms to use when searching wikipedia.
// If this number is too high, you might get no results.
public static final int MAX_SEARCH_TERMS = 3;

// max # of articles to retrieve from wikipedia at once.
// This is limited by Wikipedia as well, so a too-high number
// shouldn't blow stuff up
public static final int WIKIPEDIA_MAX_ARTICLE_COUNT = 50;

// list of possible actions to take on blogs
public enum BLOG_ACTION {
    ADD_ENTRY,
    ADD_COMMENT,
    READ,
    SUBSCRIBE,
    UNSUBSCRIBE,
    UPDATE,
    VOTE
}

// list of possible actions to take on wikis
public enum WIKI_ACTION {
    ADD_ENTRY,
    ADD_COMMENT,
    READ,
    SUBSCRIBE,
    UNSUBSCRIBE,
    UPDATE,
    VOTE
}

// list of possible actions to take on message boards
public enum MB_ACTION {
    ADD_MESSAGE,
    ADD_REPLY,
}
}
