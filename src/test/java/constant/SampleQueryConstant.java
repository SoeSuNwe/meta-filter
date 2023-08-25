package constant;

public class SampleQueryConstant {

    public static final String BINARY_FILER = "{" +
            "\"filter\":" +
            "{\"firstName\":{\"contains\":\"Saurabh\"}}" +
            "}";

    public static final String BINARY_FILER_INT = "{" +
            "\"filter\" :" +
            "{ \"age\": {\"gte\": \"25\" }}" +
            "}";
    public static final String COMPOUND_FILER_WITH_OR = """
            {"filter" : {
                  "or" : [{ "firstName" : {"contains" : "Saurabh"}},{ "lastName" : {"equals" : "Jaiswal"}}]
                }}""";

    public static final String COMPOUND_FILER_WITH_AND = """
            {
            "filter" : {
                  "and" : [{ "firstName" : {"contains" : "Saurabh"}},{ "lastName" : {"equals" : "Jaiswal"}}]
                }     }""";

    public static final String COMPOUND_FILER_WITH_AND_OR = """
            {
            "filter" :{
                  "and" : [
                    { "firstName" : {"contains" : "Saurabh"}},
                    { "or" : [{ "lastName": {"equals" : "Jaiswal"}},{ "age": {"gte": "25"}}]}       ]
                }}""";
    public static final String COMPOUND_FILER_WITH_OR_AND = """
            {
            "filter" : {
                  "or" : [
                    { "firstName" : {"contains" : "Saurabh"}},
                    { "and" : [{ "lastName": {"equals" : "Jaiswal"}},{ "age": {"gte": "25"}}
                    ]}]
                }}""";

    public static final String COMPOUND_FILER_WITH_OR_OR_AND = """
            {
            "filter" : {
                  "or" : [
                      { "firstName": {"contains": "Saurabh"}},
                      { "lastName": {"equals": "Jaiswal"}},
                      { "and" : [
                         { "firstName": {"equals": "Vinod"}},
                         { "age": {"gte": 30}}
                      ]}
                    ]
                }}""";

    public static final String COMPOUND_FILER_WITH_AND_AND_OR = """
            {
            "filter" : {
                  "and" : [
                      { "firstName": {"starts": "Saurabh"}},
                      { "lastName": {"equals": "Jaiswal"}},
                      { "or" : [
                         { "firstName": {"ends": "Vinod"}},
                         { "age": {"lte": 30}}
                      ]}
                    ]
                } }""";
    public static final String NOT_FILTER = """
            {
            "filter": {
                  "not" : { "firstName" : {"equals" : "Saurabh"} }
              }}""";

    public static final String COMPOUND_DATE_FILTER = """
            {
            "filter" :  {
                  "or" : [
                { "lastName" : {"equals" : "Jaiswal"}},   {"birthDate" :{"gt" : "1996-12-19T16:39:57-08:00"}}]
                } }""";

//    public static final String FILTER_WITH_OTHER_ARGS = "{\n" +
//            "  searchEmployees (id: \"123\", filter : {\n" +
//            "        firstName : {equals: \"Saurabh\"}\n" +
//            "    }) {\n" +
//            "      firstName\n" +
//            "      lastName\n" +
//            "      age\n" +
//            "    }\n" +
//            "}";
//
//    public static final String FILTER_WITH_VARIABLE = "query searchEmployeesWithFilter ($employeeFilter :  " +
//            "EmployeeFilter = {\n" +
//            "      and : [{ firstName : {contains : \"Saurabh\"}},{ lastName : {equals : \"Jaiswal\"}}]\n" +
//            "    }){\n" +
//            "\"filter\" :" + $employeeFilter)
//
//    {\n " +
//        "      firstName\n" +
//                "      lastName\n" +
//                "      age\n" +
//                "    }\n" +
//                "}";
//
//        public static final String LAST_NAME_IN = "{\n" +
//                "  searchEmployees(filter: {\n" +
//                "    lastName: {in: [\"Jaiswal\",\"Gupta\",\"Kumar\"]}\n" +
//                "  })\n" +
//                "  {\n" +
//                "    firstName\n" +
//                "    lastName\n" +
//                "  }\n" +
//                "}";
//
//        public static final String AGE_IN = "{\n" +
//                "  searchEmployees(filter: {\n" +
//                "    age: {in: [32, 35, 40]}\n" +
//                "  })\n" +
//                "  {\n" +
//                "    firstName\n" +
//                "    lastName\n" +
//                "  }\n" +
//                "}";
//
//        public static final String INVALID_FILTER = "{\n" +
//                "  searchEmployees(filter: {\n" +
//                "    firstName : {contains: \"Saurabh\"}\n" +
//                "    or: [\n" +
//                "      {lastName: {contains: \"Smith\"}},\n" +
//                "      {age: {lte: 30}}\n" +
//                "    ]\n" +
//                "  }) {\n" +
//                "    firstName\n" +
//                "    lastName\n" +
//                "    age\n" +
//                "  }\n" +
//                "}";
//

//
//        public static final String NOT_COMPOUND_FILTER = "{\n" +
//                "  searchEmployees(filter: {\n" +
//                "      not : { and : [\n" +
//                "        {firstName : {equals: \"Saurabh\"}},\n" +
//                "        {lastName: {contains: \"Jaiswal\"}}\n" +
//                "      ]}\n" +
//                "  }) {\n" +
//                "    firstName\n" +
//                "    lastName\n" +
//                "  }\n" +
//                "}";
//
//        public static final String COMPOUND_NOT_FILTER = "{\n" +
//                "  searchEmployees(filter: {\n" +
//                "      and : [\n" +
//                "        {firstName : {equals: \"Saurabh\"}},\n" +
//                "        { not: {lastName: {contains: \"Jaiswal\"}}}\n" +
//                "      ]\n" +
//                "  }) {\n" +
//                "    firstName\n" +
//                "    lastName\n" +
//                "  }\n" +
//                "}";
//
//        public static final String FIRST_NAME_STARTS = "{\n" +
//                "  searchEmployees(filter: {\n" +
//                "    firstName : {starts: \"Sa\"}\n" +
//                "  }) {\n" +
//                "    firstName\n" +
//                "    lastName\n" +
//                "  }\n" +
//                "}";
//        public static final String FIRST_NAME_CONTAINS = "{\n" +
//                "  searchEmployees(filter: {\n" +
//                "    firstName : {contains: \"ura\"}\n" +
//                "  }) {\n" +
//                "    firstName\n" +
//                "    lastName\n" +
//                "  }\n" +
//                "}";
//
//        public static final String FIRST_NAME_ENDS = "{\n" +
//                "  searchEmployees(filter: {\n" +
//                "    firstName : {ends: \"bh\"}\n" +
//                "  }) {\n" +
//                "    firstName\n" +
//                "    lastName\n" +
//                "  }\n" +
//                "}";
}
