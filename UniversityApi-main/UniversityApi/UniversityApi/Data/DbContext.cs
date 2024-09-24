using Dapper;
using Microsoft.Data.Sqlite;
using UniversityApi.DTO;
using UniversityApi.Models;
using UniversityApi.Repositories.Interfaces;

namespace UniversityApi.Repositories;

public class DbContext : IDbContext
{
    private readonly string _connectionString;

    public DbContext(string connectionString)
    {
        _connectionString = connectionString;
    }

    #region Students
    public async Task<IEnumerable<Student>> GetAllStudents()
    {
        using (var connection = new SqliteConnection(_connectionString))
        {
            connection.Open();
            var sql = @"
                SELECT 
                    student_id as StudentId, 
                    first_name as FirstName, 
                    last_name as LastName, 
                    birthdate as Birthdate, 
                    grade_level as GradeLevel 
                FROM students";
            return await connection.QueryAsync<Student>(sql);
        }
    }

    public async Task<Student> GetStudentById(int id)
    {
        using (var connection = new SqliteConnection(_connectionString))
        {
            connection.Open();
            var sql = @"
                SELECT 
                    student_id as StudentId, 
                    first_name as FirstName, 
                    last_name as LastName, 
                    birthdate as Birthdate, 
                    grade_level as GradeLevel 
                FROM students 
                WHERE student_id = @Id";
            var students = await connection.QueryAsync<Student>(sql, new { Id = id });
            return students.FirstOrDefault();
        }
    }

    public async Task<IEnumerable<CourseDetailsDto>> GetCoursesByStudentId(int studentId)
    {
        var sql = @"
        SELECT 
            c.course_id AS CourseId, 
            e.enrollment_id AS EnrollmentId,
            c.course_name AS CourseName, 
            c.credits, 
            MAX(g.grade) AS Grade
        FROM 
            enrollments e
        JOIN 
            courses c ON e.course_id = c.course_id
        LEFT JOIN 
            grades g ON e.enrollment_id = g.enrollment_id
        WHERE 
            e.student_id = @StudentId
        GROUP BY 
            c.course_id, c.course_name, c.credits";

        using (var connection = new SqliteConnection(_connectionString))
        {
            connection.Open();

            var result = await connection.QueryAsync<CourseDetailsDto>(sql, new { StudentId = studentId });

            return result;
        }
    }

    public async Task<int> AddStudent(Student student)
    {
        var sql = @"
        INSERT INTO students (first_name, last_name, birthdate, grade_level)
        VALUES (@FirstName, @LastName, @Birthdate, @GradeLevel);
        SELECT last_insert_rowid();";

        using (var connection = new SqliteConnection(_connectionString))
        {
            connection.Open();

            var studentId = await connection.ExecuteScalarAsync<int>(sql, student);

            return studentId;
        }
    }

    public async Task UpdateStudent(Student studentToUpdate)
    {
        const string sql = @"
        UPDATE students
        SET first_name = @FirstName, last_name = @LastName, birthdate = @Birthdate, grade_level = @GradeLevel
        WHERE student_id = @StudentId";

        using (var connection = new SqliteConnection(_connectionString))
        {
            connection.Open();

            await connection.ExecuteAsync(sql, studentToUpdate);
        }
    }

    public async Task DeleteStudent(int id)
    {
        const string sql = "DELETE FROM students WHERE student_id = @StudentId";

        using (var connection = new SqliteConnection(_connectionString))
        {
            connection.Open();

            await connection.ExecuteAsync(sql, new { StudentId = id });
        }
    }
    #endregion

    #region Teachers
    public async Task<IEnumerable<Teacher>> GetAllTeachers()
    {
        var sql = @"SELECT teacher_id AS TeacherId, 
                        first_name AS FirstName, 
                        last_name AS LastName, 
                        hire_date AS HireDate 
                    FROM teachers";

        using (var connection = new SqliteConnection(_connectionString))
        {
            connection.Open();
            var teachers = await connection.QueryAsync<Teacher>(sql);
            return teachers;
        }
    }

    public async Task<Teacher> GetTeacherById(int teacherId)
    {
        var sql = @"
        SELECT 
            teacher_id AS TeacherId, 
            first_name AS FirstName, 
            last_name AS LastName, 
            hire_date AS HireDate
        FROM 
            teachers
        WHERE 
            teacher_id = @TeacherId";

        using (var connection = new SqliteConnection(_connectionString))
        {
            connection.Open();
            return await connection.QueryFirstOrDefaultAsync<Teacher>(sql, new { TeacherId = teacherId });
        }
    }

    public async Task<IEnumerable<Course>> GetCoursesByTeacherId(int teacherId)
    {
        var sql = @"
        SELECT DISTINCT 
            c.course_id AS CourseId, 
            c.course_name AS CourseName, 
            c.credits
        FROM 
            enrollments e
        JOIN 
            courses c ON e.course_id = c.course_id
        WHERE 
            e.teacher_id = @TeacherId";

        using (var connection = new SqliteConnection(_connectionString))
        {
            connection.Open();
            var courses = await connection.QueryAsync<Course>(sql, new { TeacherId = teacherId });
            return courses;
        }
    }

    public async Task<int> AddTeacher(Teacher teacher)
    {
        var sql = @"
        INSERT INTO teachers (first_name, last_name, hire_date)
        VALUES (@FirstName, @LastName, @HireDate);
        SELECT last_insert_rowid();";

        using (var connection = new SqliteConnection(_connectionString))
        {
            connection.Open();

            var teacherId = await connection.ExecuteScalarAsync<int>(sql, teacher);

            return teacherId;
        }
    }

    public async Task UpdateTeacher(Teacher teacherToUpdate)
    {
        const string sql = @"
        UPDATE teachers
        SET first_name = @FirstName, last_name = @LastName, hire_date = @HireDate
        WHERE teacher_id = @TeacherId";

        using (var connection = new SqliteConnection(_connectionString))
        {
            connection.Open();

            await connection.ExecuteAsync(sql, teacherToUpdate);
        }
    }

    public async Task DeleteTeacher(int id)
    {
        const string sql = "DELETE FROM teachers WHERE teacher_id = @TeacherId";

        using (var connection = new SqliteConnection(_connectionString))
        {
            connection.Open();

            await connection.ExecuteAsync(sql, new { TeacherId = id });
        }
    }
    #endregion

    #region Courses
    public async Task<IEnumerable<Course>> GetAllCourses()
    {
        var sql = @"
            SELECT 
                course_id AS CourseId, 
                course_name AS CourseName, 
                credits
            FROM 
                courses";

        using (var connection = new SqliteConnection(_connectionString))
        {
            connection.Open();
            var courses = await connection.QueryAsync<Course>(sql);
            return courses;
        }
    }

    public async Task<Course> GetCourseById(int id)
    {
        const string sql = "SELECT course_id AS CourseId, course_name AS CourseName, credits FROM courses WHERE course_id = @CourseId";

        using (var connection = new SqliteConnection(_connectionString))
        {
            connection.Open();

            return await connection.QueryFirstOrDefaultAsync<Course>(sql, new { CourseId = id });
        }
    }

    public async Task<int> AddCourse(Course course)
    {
        var sql = @"
            INSERT INTO courses (course_name, credits)
            VALUES (@CourseName, @Credits);
            SELECT last_insert_rowid();";

        using (var connection = new SqliteConnection(_connectionString))
        {
            connection.Open();
            var courseId = await connection.ExecuteScalarAsync<int>(sql, course);

            return courseId;
        }
    }

    public async Task DeleteCourse(int id)
    {
        const string sql = "DELETE FROM courses WHERE course_id = @CourseId";

        using (var connection = new SqliteConnection(_connectionString))
        {
            connection.Open();

            await connection.ExecuteAsync(sql, new { CourseId = id });
        }
    }
    #endregion

    #region Enrollments
    public async Task<int> AddEnrollment(Enrollment enrollment)
    {
        var sql = @"
            INSERT INTO enrollments (student_id, course_id, teacher_id, enrollment_date)
            VALUES (@StudentId, @CourseId, @TeacherId, @EnrollmentDate);
            SELECT last_insert_rowid();";

        using (var connection = new SqliteConnection(_connectionString))
        {
            connection.Open();

            var enrollmentId = await connection.ExecuteScalarAsync<int>(sql, enrollment);

            return enrollmentId;
        }
    }

    public async Task<Enrollment> GetEnrollmentByStudentAndCourseId(int studentId, int courseId)
    {
        var sql = @"
            SELECT enrollment_id AS EnrollmentId, 
                   student_id AS StudentId, 
                   course_id AS CourseId, 
                   teacher_id AS TeacherId, 
                   enrollment_date AS EnrollmentDate
            FROM enrollments
            WHERE student_id = @StudentId AND course_id = @CourseId";

        using (var connection = new SqliteConnection(_connectionString))
        {
            connection.Open();

            var enrollment = await connection.QueryFirstOrDefaultAsync<Enrollment>(sql, new { StudentId = studentId, CourseId = courseId });

            return enrollment;
        }
    }

    public async Task DeleteEnrollment(int enrollmentId)
    {
        const string sql = "DELETE FROM enrollments WHERE enrollment_id = @EnrollmentId";

        using (var connection = new SqliteConnection(_connectionString))
        {
            connection.Open();

            await connection.ExecuteAsync(sql, new { EnrollmentId = enrollmentId });
        }
    }
    #endregion

    #region Grades
    public async Task<int> AddGrade(Grade grade)
    {
        var sql = @"
            INSERT INTO grades (enrollment_id, grade)
            VALUES (@EnrollmentId, @GradeValue);
            SELECT last_insert_rowid();";

        using (var connection = new SqliteConnection(_connectionString))
        {
            connection.Open();
            var gradeId = await connection.ExecuteScalarAsync<int>(sql, grade);

            return gradeId;
        }
    }

    public async Task<Grade> GetGradeById(int id)
    {
        const string sql = "SELECT grade_id AS GradeId, enrollment_id AS EnrollmentId, grade FROM grades WHERE grade_id = @GradeId";

        using (var connection = new SqliteConnection(_connectionString))
        {
            connection.Open();

            return await connection.QueryFirstOrDefaultAsync<Grade>(sql, new { GradeId = id });
        }
    }

    public async Task DeleteGrade(int id)
    {
        const string sql = "DELETE FROM grades WHERE grade_id = @GradeId";

        using (var connection = new SqliteConnection(_connectionString))
        {
            connection.Open();

            await connection.ExecuteAsync(sql, new { GradeId = id });
        }
    }
    #endregion
}
