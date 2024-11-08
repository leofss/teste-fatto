import axios from 'axios';

const apiUrl = import.meta.env.REACT_APP_API_URL

const TaskService = {

    addTask: async (task) => {
        try {
            const response = await axios.post(apiUrl, task);
            return response.data; 
        } catch (error) {
            throw error; 
        }
    },

    getAllTasks: async () => {
        try {
            const response = await axios.get(apiUrl);
            return response; 
        } catch (error) {
            throw error; 
        }
    },

    deleteTask: async (taskId) => {
        try {
            const response = await axios.delete(`${apiUrl}/${taskId}`);
            return response.data; 
        } catch (error) {
            throw error; 
        }
    },

    editTask: async (taskId, taskData) => {

        try {
            console.log(`${apiUrl}/${taskId}`)
            console.log("Task Data:", taskData); 
            const response = await axios.put(`${apiUrl}/${taskId}`, taskData, {
                headers: {
                    'Content-Type': 'application/json', 
                }
            });
            console.log(response)
            return response.data; 
        } catch (error) {
            console.error("Error editing task:", error);
            throw error; 
        }
    }

};

export default TaskService;