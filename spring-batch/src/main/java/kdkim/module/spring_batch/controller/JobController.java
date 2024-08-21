package kdkim.module.spring_batch.controller;

import kdkim.module.spring_batch.repository.BatchRepository;
import kdkim.module.spring_batch.repository.DailyRepository;
import kdkim.module.spring_batch.repository.LedgerRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class JobController {
    private final JobLauncher jobLauncher;
    private final Map<String, Job> jobs;
    private final BatchRepository batchRepository;
    private final DailyRepository dailyRepository;

    @GetMapping("/api/job/launch/{jobName}")
    public void launchJob(@PathVariable String jobName) throws Exception{
        Job job = Optional.ofNullable(jobs.get(jobName))
                .orElseThrow(() -> new BadRequestException(jobName + " is not exist"));
        // JobParameters에 현재 시간을 추가하여 매번 새로운 JobParameters를 사용
        JobParameters jobParameters = new JobParametersBuilder()
                .addDate("runDate", new Date())
                .toJobParameters();
        System.out.println("------------------------------------------------------");
        System.out.println(job);
        System.out.println(jobParameters);
        System.out.println("------------------------------------------------------");
        jobLauncher.run(job, jobParameters);


        if (jobName.toString() == "copyLedgerJob") {
            batchRepository.findAll().forEach(System.out::println);
        } else {
            dailyRepository.findAll().forEach(System.out::println);
        }
    }

}
