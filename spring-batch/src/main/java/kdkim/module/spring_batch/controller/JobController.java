package kdkim.module.spring_batch.controller;

import kdkim.module.spring_batch.repository.BatchRepository;
import kdkim.module.spring_batch.repository.DailyRepository;
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

    /**
     * 특정 잡을 실행하는 API 엔드포인트
     *
     * @param jobName 실행할 잡의 이름
     * @throws Exception 잡 실행 중 발생한 예외
     */
    @GetMapping("/api/job/launch/{jobName}")
    public void launchJob(@PathVariable String jobName) throws Exception {
        // 요청된 jobName에 해당하는 Job을 가져옴. 없으면 예외 발생
        Job job = Optional.ofNullable(jobs.get(jobName))
                .orElseThrow(() -> new BadRequestException(jobName + " is not exist"));

        // 현재 시간을 포함하는 JobParameters 생성
        JobParameters jobParameters = new JobParametersBuilder()
                .addDate("runDate", new Date())
                .toJobParameters();

        // 잡 실행
        jobLauncher.run(job, jobParameters);

        // 잡 이름에 따라 데이터 출력
        if (jobName.equals("copyLedgerJob")) {
            batchRepository.findAll().forEach(System.out::println);
        } else {
            dailyRepository.findAll().forEach(System.out::println);
        }
    }
}
