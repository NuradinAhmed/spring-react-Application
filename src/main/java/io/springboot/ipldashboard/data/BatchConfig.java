
package io.springboot.ipldashboard.data;

import io.springboot.ipldashboard.model.MatchOutput;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.core.io.ClassPathResource;

import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.launch.support.RunIdIncrementer;


@Configuration
@EnableBatchProcessing
public class BatchConfig {
    //Create A Constant for all the field names that would be read in the read bean and then reference down below.
    private final String[] Field_Names = new String[] {

            "id", "city", "date", "player_of_match", "venue", "neutral_venue", "team1", "team2",
            "toss_winner","toss_decision", "winner", "result", "result_margin", "eliminator", "method",
            "umpire1", "umpire2"

    };

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;




    /*
1. JOB READER


    Adding the following beans to this batchConfig class to define a reader, a processor and writer!
     */
    @Bean
    public FlatFileItemReader<MatchInput> reader() {
        return new FlatFileItemReaderBuilder<MatchInput>()
                .name("personItemReader")
                .resource(new ClassPathResource("IPL-Matches-Data-2008-2020.csv"))
                .delimited()
                .names(Field_Names)   //here we've passed string of an array of all fields that it needs to read!

                //1. once you get each element in the field names from the csv files then
                .fieldSetMapper(new BeanWrapperFieldSetMapper<MatchInput>() {
                    {
                        //2.create the matchinput class instance from each input values
                        setTargetType(MatchInput.class);
                    }
                })
                .build();
    }



/*
2. JOB PROCESSOR
 */

    //2. Processor class - here is a processor and it know what b/c we've written the logic in the matchDataProcessor class.
    // so it takes in and returns it as is.

    //processor() creates an instance of MatchDataProcessor that we define earlier - meant to convert the data format from matchinput to matchout as well
        // as applying the logic for the bat/field per toss-decision win handler.
    @Bean
    public MatchDataProcessor processor() {
        return new MatchDataProcessor();
    }





/*
JOB WRITER!
 */

    //3. Final output is to write it into our database using JdbcBatchItemWriterBuilder - it takes an instance of the our Matchout class inserts into sql table that was
        // created by the jpa entity in the output class and then gets passed with its values.
    /*
            Writer(DataSource) creates an itemWriter. Its aimed at a JDBC destination and automatically gets a copy of the dataSource
             created by @EnableBatchProcessing. It includes the SQL statement to insert a people(dataInput), driven by Java bean properties.
    */

    @Bean
    public JdbcBatchItemWriter<MatchOutput> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<MatchOutput>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                //we have to have table created. but we could use the jPA create one for us annotating with Entity at the model level.
                .sql("INSERT INTO people (id, city, date, player_of_match, venue, team1, team2, toss_winner, toss_decision, match_winner, result, result_margin, umpire1, umpire2) "
                        + "VALUES (: id, : city, :date, : playerOfMatch, :venue, : team1, : team2, : tossWinner, :tossDecision, : matchWinner, :result, resultMargin, :umpire1, :umpire2)")
                .dataSource(dataSource)
                .build();
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
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<MatchOutput> writer) {
        return stepBuilderFactory.get("step1")
                .<MatchInput, MatchOutput> chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }







}