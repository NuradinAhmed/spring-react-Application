
package io.springboot.ipldashboard.data;
import io.springboot.ipldashboard.model.Match;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;


    @Configuration
    @EnableBatchProcessing
    public class BatchConfig {

        private final String[] FIELD_NAMES = new String[] { "id", "city", "date", "player_of_match", "venue",
                "neutral_venue", "team1", "team2", "toss_winner", "toss_decision", "winner", "result", "result_margin",
                "eliminator", "method", "umpire1", "umpire2" };

        @Autowired
        public JobBuilderFactory jobBuilderFactory;

        @Autowired
        public StepBuilderFactory stepBuilderFactory;
        //1. JOB READER
        //    Adding the following beans to this batchConfig class to define a reader, a processor and writer!
        //     */
        @Bean
        public FlatFileItemReader<MatchInput> reader() {
            return new FlatFileItemReaderBuilder<MatchInput>().name("MatchItemReader")
                    .resource(new ClassPathResource("match-data.csv")).delimited().names(FIELD_NAMES)//passed string of an array of all fields that it needs to read!
                    .fieldSetMapper(new BeanWrapperFieldSetMapper<MatchInput>() { //1. once you get each element in the field names from the csv files then

                        {
                            setTargetType(MatchInput.class); //2.create the matchinput class instance from each input values
                        }
                    }).build();
        }

        @Bean
        public MatchDataProcessor processor() {
            return new MatchDataProcessor();
        }

        //3. Final output is to write it into our database using JdbcBatchItemWriterBuilder - it takes an instance of the our Matchout class inserts into sql table that was
        // created by the jpa entity in the output class and then gets passed with its values.
        /*
            Writer(DataSource) creates an itemWriter. Its aimed at a JDBC destination and automatically gets a copy of the dataSource
             created by @EnableBatchProcessing. It includes the SQL statement to insert a people(dataInput), driven by Java bean properties.
        */
        @Bean
        public JdbcBatchItemWriter<Match> writer(DataSource dataSource) {
            return new JdbcBatchItemWriterBuilder<Match>()
                    .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                    .sql("INSERT INTO match(id, city, date, player_of_match, venue, team1, team2, toss_winner, toss_decision, match_winner, result, result_margin, umpire1, umpire2) "
                            + " VALUES (:id, :city, :date, :playerOfMatch, :venue, :team1, :team2, :tossWinner, :tossDecision, :matchWinner, :result, :resultMargin, :umpire1, :umpire2)")
                    .dataSource(dataSource).build();
        }
        /*
        CREATION/DEFINE OF JOB!

            The first method defines the job, and the second one defines a single step. Jobs are built from steps, where each step can involve a reader, a processor, and a writer.
            In this job definition, you need an incrementer, because jobs use a database to maintain execution state. You then list each step, (though this job has only one step).
            The job ends, and the Java API produces a perfectly configured job.

            In the step definition, you define how much data to write at a time. In this case, it writes up to ten records at a time.
            Next, you configure the reader, processor, and writer by using the beans injected earlier.
        */
        @Bean
        public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
            return jobBuilderFactory
                    .get("importUserJob")
                    .incrementer(new RunIdIncrementer())
                    .listener(listener)
                    .flow(step1)
                    .end()
                    .build();
        }

        @Bean
        public Step step1(JdbcBatchItemWriter<Match> writer) {
            return stepBuilderFactory
                    .get("step1")
                    .<MatchInput, Match>chunk(10)
                    .reader(reader())
                    .processor(processor())
                    .writer(writer)
                    .build();
        }

}