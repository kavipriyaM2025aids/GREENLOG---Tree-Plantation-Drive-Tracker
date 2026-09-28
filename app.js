const API_BASE = '/api';

const app = {
    state: {
        drives: [],
        volunteers: [],
        trees: [],
        dueCheckins: [],
        leaderboard: []
    },

    init() {
        this.ui.setupNavigation();
        this.ui.setupLandingPage();
        this.ui.updateDate();
    },

    async fetchAPI(endpoint, options = {}) {
        options.headers = { 'Content-Type': 'application/json', ...options.headers };
        try {
            const response = await fetch(`${API_BASE}${endpoint}`, options);
            if(response.status === 204) return null;
            
            let data;
            try {
                data = await response.json();
            } catch (e) {
                const text = await response.text();
                if (!response.ok) throw new Error(text || 'API Request Failed');
                return text;
            }
            
            if(!response.ok) {
                throw new Error(data.message || data.error || 'API Request Failed');
            }
            return data;
        } catch (error) {
            app.ui.showToast(error.message, 'error');
            throw error;
        }
    },

    ui: {
        setupLandingPage() {
            // Generate glowing particles
            const particlesContainer = document.getElementById('particles');
            for(let i = 0; i < 40; i++) {
                const p = document.createElement('div');
                p.className = 'particle';
                const size = Math.random() * 6 + 2;
                p.style.width = `${size}px`;
                p.style.height = `${size}px`;
                p.style.left = `${Math.random() * 100}%`;
                p.style.animationDuration = `${Math.random() * 8 + 6}s`;
                p.style.animationDelay = `${Math.random() * 5}s`;
                particlesContainer.appendChild(p);
            }

            // Loading sequence (0 to 100)
            const pctEl = document.getElementById('loading-pct');
            const fillEl = document.getElementById('progress-fill');
            let pct = 0;
            
            const loadInterval = setInterval(() => {
                pct += Math.floor(Math.random() * 8) + 1;
                if(pct >= 100) {
                    pct = 100;
                    clearInterval(loadInterval);
                    
                    setTimeout(() => {
                        const loadingPhase = document.getElementById('loading-phase');
                        loadingPhase.classList.remove('loading-active');
                        loadingPhase.classList.add('hidden');
                        
                        setTimeout(() => {
                            const landingPhase = document.getElementById('landing-phase');
                            landingPhase.classList.remove('hidden');
                            landingPhase.classList.add('loading-active');
                        }, 800); 
                    }, 500); 
                }
                
                pctEl.innerText = pct;
                fillEl.style.width = `${pct}%`;
            }, 60);

            // Enter Button Transition
            const enterBtn = document.getElementById('enter-btn');
            if(enterBtn) {
                enterBtn.addEventListener('click', () => {
                    const wrapper = document.getElementById('hero-wrapper');
                    
                    wrapper.classList.add('slide-up');
                    
                    setTimeout(() => {
                        wrapper.style.display = 'none';
                        const appContainer = document.getElementById('app-container');
                        appContainer.classList.remove('hidden');
                        
                        setTimeout(() => {
                            appContainer.classList.add('visible');
                            app.dashboard.load();
                        }, 50);
                    }, 1200); 
                });
            }
            
            // Mobile toggle
            document.getElementById('mobile-toggle').addEventListener('click', () => {
                document.getElementById('sidebar').classList.toggle('open');
            });
        },

        setupNavigation() {
            const pageTitles = {
                'dashboard': ['Dashboard', 'Monitor your plantation ecosystem'],
                'drives': ['Plantation Drives', 'Organize and monitor plantation activities'],
                'volunteers': ['Volunteers', 'Manage and track your GreenLog community'],
                'trees': ['Tree Registry', 'Track every planted tree'],
                'checkins': ['Tree Check-ins', 'Record periodic tree health updates'],
                'reports': ['Analytics', 'Data-driven insights for a greener future']
            };

            document.querySelectorAll('.nav-links li').forEach(link => {
                link.addEventListener('click', (e) => {
                    document.querySelectorAll('.nav-links li').forEach(l => l.classList.remove('active'));
                    e.currentTarget.classList.add('active');
                    
                    const target = e.currentTarget.getAttribute('data-target');
                    document.querySelectorAll('.page').forEach(p => p.classList.remove('active'));
                    document.getElementById(target).classList.add('active');
                    
                    // Update Header text
                    document.getElementById('page-title').innerText = pageTitles[target][0];
                    document.getElementById('page-subtitle').innerText = pageTitles[target][1];
                    
                    if(window.innerWidth <= 768) document.getElementById('sidebar').classList.remove('open');
                    
                    // Route to specific page logic
                    if(target === 'dashboard') app.dashboard.load();
                    if(target === 'drives') app.drives.load();
                    if(target === 'volunteers') app.volunteers.load();
                    if(target === 'trees') app.trees.load();
                    if(target === 'checkins') app.checkins.load();
                    if(target === 'reports') app.reports.init();
                });
            });
        },

        updateDate() {
            const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' };
            document.getElementById('current-date').innerText = new Date().toLocaleDateString('en-US', options);
        },

        showToast(message, type = 'success') {
            const container = document.getElementById('toast-container');
            const toast = document.createElement('div');
            toast.className = `toast ${type}`;
            const icon = type === 'success' ? 'fa-check-circle' : 'fa-exclamation-circle';
            toast.innerHTML = `<i class="fas ${icon} toast-icon"></i> <span class="toast-msg">${message}</span>`;
            
            container.appendChild(toast);
            
            setTimeout(() => {
                toast.classList.add('fadeOut');
                setTimeout(() => toast.remove(), 500);
            }, 4000);
        },

        openModal(id) {
            document.getElementById(id).classList.add('show');
        },

        closeModal(id) {
            document.getElementById(id).classList.remove('show');
            const form = document.querySelector(`#${id} form`);
            if(form) form.reset();
            const hiddenId = document.querySelector(`#${id} input[type="hidden"]`);
            if(hiddenId) hiddenId.value = '';
            
            if(id === 'checkinModal') {
                document.getElementById('deadWarning').classList.add('hidden');
                document.getElementById('nextDateGroup').classList.remove('hidden');
            }
        },
        
        animateNumber(elementId, endValue, duration) {
            const element = document.getElementById(elementId);
            if (!element) return;
            const startTime = performance.now();
            
            function update(currentTime) {
                const elapsed = currentTime - startTime;
                const progress = Math.min(elapsed / duration, 1);
                const easeProgress = 1 - Math.pow(1 - progress, 4); 
                const current = Math.floor(easeProgress * endValue);
                
                element.innerText = current;
                
                if (progress < 1) requestAnimationFrame(update);
                else element.innerText = endValue;
            }
            requestAnimationFrame(update);
        }
    },

    dashboard: {
        async load() {
            try {
                // Fetch Real Data
                app.state.drives = await app.fetchAPI('/drives');
                app.state.volunteers = await app.fetchAPI('/volunteers');
                app.state.trees = await app.fetchAPI('/trees');
                app.state.leaderboard = await app.fetchAPI('/volunteers/leaderboard');
                app.state.dueCheckins = await app.fetchAPI('/trees/due-checkins');

                // Animate Core Stats
                app.ui.animateNumber('stat-drives', app.state.drives.length, 1500);
                app.ui.animateNumber('stat-volunteers', app.state.volunteers.length, 1500);
                app.ui.animateNumber('stat-trees', app.state.trees.length, 1500);
                
                // Animate Impact Stats
                app.ui.animateNumber('im-trees', app.state.trees.length, 1500);
                app.ui.animateNumber('im-vols', app.state.volunteers.length, 1500);
                app.ui.animateNumber('im-drives', app.state.drives.length, 1500);

                // Calculate Survival
                const aliveTrees = app.state.trees.filter(t => t.currentStatus === 'ALIVE').length;
                const deadTrees = app.state.trees.length - aliveTrees;
                const survivalRate = app.state.trees.length > 0 ? ((aliveTrees / app.state.trees.length) * 100) : 0;
                
                app.ui.animateNumber('po-total', app.state.trees.length, 1500);
                app.ui.animateNumber('po-alive-val', aliveTrees, 1500);
                app.ui.animateNumber('po-dead-val', deadTrees, 1500);

                // Animate Survival Rate ring and text
                const rateEl = document.getElementById('stat-rate');
                const ringEl = document.getElementById('survival-ring-fill');
                const statusEl = document.getElementById('survival-status');
                
                const poRateEl = document.getElementById('po-rate');
                const poRingEl = document.getElementById('po-ring-fill');
                
                let startRate = 0;
                const rateInterval = setInterval(() => {
                    startRate += 1;
                    if(startRate >= survivalRate) {
                        startRate = survivalRate;
                        clearInterval(rateInterval);
                    }
                    const textRate = `${startRate.toFixed(2)}%`;
                    rateEl.innerText = textRate;
                    poRateEl.innerText = textRate;
                    ringEl.setAttribute('stroke-dasharray', `${startRate}, 100`);
                    poRingEl.setAttribute('stroke-dasharray', `${startRate}, 100`);
                }, 15);
                
                if (survivalRate >= 80) statusEl.innerText = "Healthy ecosystem 🌱";
                else if (survivalRate >= 50) statusEl.innerText = "Needs attention ⚠️";
                else statusEl.innerText = "Critical state 🔴";
                
                // Animate Analytics Bars
                setTimeout(() => {
                    document.getElementById('po-alive-bar').style.width = `${survivalRate}%`;
                    document.getElementById('po-alive-bar').parentElement.nextElementSibling.innerText = `${survivalRate.toFixed(2)}%`;
                    document.getElementById('po-dead-bar').style.width = `${100 - survivalRate}%`;
                    document.getElementById('po-dead-bar').parentElement.nextElementSibling.innerText = `${(100 - survivalRate).toFixed(2)}%`;
                }, 200);

                this.renderDueCheckins();
                this.renderLeaderboard();
                
                // Trigger CSS Stagger Animations for Panels
                document.querySelectorAll('.stagger-animate').forEach(el => el.classList.remove('stagger-animate'));
                setTimeout(() => {
                    document.querySelectorAll('.stagger-1, .stagger-2, .stagger-3, .stagger-4').forEach(el => el.classList.add('stagger-animate'));
                }, 50);

            } catch(e) { console.error(e); }
        },
        
        renderDueCheckins() {
            const container = document.getElementById('due-checkins-list');
            if(app.state.dueCheckins.length === 0) {
                container.innerHTML = `<div style="padding: 30px 20px; text-align: center; color: var(--text-muted);"><i class="fas fa-check-circle text-success" style="font-size: 3rem; margin-bottom: 15px; display: block; opacity: 0.5;"></i> 🌱 All trees are up to date!</div>`;
                return;
            }
            
            container.innerHTML = app.state.dueCheckins.map((t, i) => `
                <div class="premium-due-item stagger-child" style="animation-delay: ${i * 0.1}s">
                    <div class="due-left">
                        <div class="due-icon"><i class="fas fa-tree"></i></div>
                        <div class="due-text">
                            <h4>${t.species} (ID: ${t.treeId})</h4>
                            <p><i class="fas fa-map-marker-alt"></i> ${t.location} | <i class="fas fa-leaf"></i> ${t.drive.driveName}</p>
                        </div>
                    </div>
                    <span class="due-badge">DUE</span>
                </div>
            `).join('');
        },
        
        renderLeaderboard() {
            const container = document.getElementById('leaderboard-list');
            if(app.state.leaderboard.length === 0) {
                container.innerHTML = `<div style="padding: 20px; text-align: center; color: var(--text-muted);">No data available yet.</div>`;
                return;
            }
            
            container.innerHTML = app.state.leaderboard.map((row, index) => {
                let rankClass = index < 3 ? `medal-${index + 1}` : 'medal-other';
                let rankDisplay = index + 1;
                
                return `
                <div class="premium-lb-item stagger-child" style="animation-delay: ${index * 0.1}s">
                    <div class="lb-medal ${rankClass}">${rankDisplay}</div>
                    <div class="lb-name"><img src="https://ui-avatars.com/api/?name=${encodeURIComponent(row[1])}&background=random&color=fff" alt="Avatar"> ${row[1]}</div>
                    <div class="lb-score"><i class="fas fa-seedling"></i> ${row[2]} Trees</div>
                </div>
            `}).join('');
        }
    },

    drives: {
        async load() {
            app.state.drives = await app.fetchAPI('/drives');
            this.render(app.state.drives);
        },
        render(data) {
            const grid = document.getElementById('drives-grid');
            grid.innerHTML = data.map(d => `
                <div class="drive-card">
                    <div class="drive-header">
                        <h3>${d.driveName}</h3>
                        <p><i class="fas fa-map-marker-alt"></i> ${d.location}</p>
                    </div>
                    <div class="drive-stats">
                        <div class="ds-item">
                            <div class="ds-val">${d.driveDate}</div>
                            <div class="ds-label">Date</div>
                        </div>
                        <div class="ds-item">
                            <div class="ds-val" style="color: var(--accent);">${d.survivalRate}%</div>
                            <div class="ds-label">Survival</div>
                        </div>
                    </div>
                    <div class="drive-actions">
                        <button class="btn-icon" onclick="app.drives.edit(${d.driveId})" title="Edit"><i class="fas fa-edit"></i></button>
                        <button class="btn-icon delete" onclick="app.drives.delete(${d.driveId})" title="Delete"><i class="fas fa-trash"></i></button>
                    </div>
                </div>
            `).join('');
        },
        async save(e) {
            e.preventDefault();
            const id = document.getElementById('driveId').value;
            const payload = {
                driveName: document.getElementById('driveName').value,
                location: document.getElementById('driveLocation').value,
                driveDate: document.getElementById('driveDate').value,
                description: document.getElementById('driveDesc').value
            };
            const method = id ? 'PUT' : 'POST';
            const url = id ? `/drives/${id}` : '/drives';
            await app.fetchAPI(url, { method, body: JSON.stringify(payload) });
            app.ui.closeModal('driveModal');
            app.ui.showToast(`Plantation drive ${id ? 'updated' : 'created'} successfully`);
            this.load();
        },
        async edit(id) {
            const drive = await app.fetchAPI(`/drives/${id}`);
            document.getElementById('driveId').value = drive.driveId;
            document.getElementById('driveName').value = drive.driveName;
            document.getElementById('driveLocation').value = drive.location;
            document.getElementById('driveDate').value = drive.driveDate;
            document.getElementById('driveDesc').value = drive.description;
            document.getElementById('driveModalTitle').innerText = 'Edit Plantation Drive';
            app.ui.openModal('driveModal');
        },
        async delete(id) {
            if(confirm('Delete this plantation drive?\nThis action cannot be undone.')) {
                await app.fetchAPI(`/drives/${id}`, { method: 'DELETE' });
                app.ui.showToast('Drive deleted successfully');
                this.load();
            }
        }
    },

    volunteers: {
        async load() {
            app.state.volunteers = await app.fetchAPI('/volunteers');
            this.render(app.state.volunteers);
        },
        render(data) {
            const tbody = document.getElementById('volunteers-table');
            tbody.innerHTML = data.map(v => `
                <tr>
                    <td><strong>${v.name}</strong></td>
                    <td>${v.email}</td>
                    <td>${v.phone || '-'}</td>
                    <td>${v.joinedDate}</td>
                    <td>
                        <button class="btn-icon" onclick="app.volunteers.edit(${v.volunteerId})"><i class="fas fa-edit"></i></button>
                        <button class="btn-icon delete" onclick="app.volunteers.delete(${v.volunteerId})"><i class="fas fa-trash"></i></button>
                    </td>
                </tr>
            `).join('');
        },
        async save(e) {
            e.preventDefault();
            const id = document.getElementById('volunteerId').value;
            const payload = {
                name: document.getElementById('volName').value,
                email: document.getElementById('volEmail').value,
                phone: document.getElementById('volPhone').value,
                joinedDate: document.getElementById('volJoined').value
            };
            const method = id ? 'PUT' : 'POST';
            const url = id ? `/volunteers/${id}` : '/volunteers';
            await app.fetchAPI(url, { method, body: JSON.stringify(payload) });
            app.ui.closeModal('volunteerModal');
            app.ui.showToast(`Volunteer ${id ? 'updated' : 'created'} successfully`);
            this.load();
        },
        async edit(id) {
            const v = await app.fetchAPI(`/volunteers/${id}`);
            document.getElementById('volunteerId').value = v.volunteerId;
            document.getElementById('volName').value = v.name;
            document.getElementById('volEmail').value = v.email;
            document.getElementById('volPhone').value = v.phone;
            document.getElementById('volJoined').value = v.joinedDate;
            document.getElementById('volunteerModalTitle').innerText = 'Edit Volunteer';
            app.ui.openModal('volunteerModal');
        },
        async delete(id) {
            if(confirm('Delete this volunteer?\nThis action cannot be undone.')) {
                await app.fetchAPI(`/volunteers/${id}`, { method: 'DELETE' });
                app.ui.showToast('Volunteer deleted successfully');
                this.load();
            }
        }
    },

    trees: {
        async load() {
            app.state.trees = await app.fetchAPI('/trees');
            this.render(app.state.trees);
            this.populateSelects();
        },
        render(data) {
            const tbody = document.getElementById('trees-table');
            tbody.innerHTML = data.map(t => `
                <tr>
                    <td>#${t.treeId}</td>
                    <td><strong>${t.species}</strong></td>
                    <td>${t.location}</td>
                    <td>${t.plantedDate}</td>
                    <td>${t.drive.driveName}</td>
                    <td>${t.volunteer.name}</td>
                    <td><span class="status-badge ${t.currentStatus.toLowerCase()}">${t.currentStatus}</span></td>
                    <td>${t.nextCheckinDate || '-'}</td>
                    <td>
                        <button class="btn-icon" onclick="app.trees.edit(${t.treeId})"><i class="fas fa-edit"></i></button>
                        <button class="btn-icon delete" onclick="app.trees.delete(${t.treeId})"><i class="fas fa-trash"></i></button>
                    </td>
                </tr>
            `).join('');
        },
        async populateSelects() {
            const drives = await app.fetchAPI('/drives');
            const vols = await app.fetchAPI('/volunteers');
            document.getElementById('treeDrive').innerHTML = '<option value="" disabled selected hidden>Select plantation drive</option>' + drives.map(d => `<option value="${d.driveId}">${d.driveName}</option>`).join('');
            document.getElementById('treeVol').innerHTML = '<option value="" disabled selected hidden>Select volunteer</option>' + vols.map(v => `<option value="${v.volunteerId}">${v.name}</option>`).join('');
        },
        async save(e) {
            e.preventDefault();
            const id = document.getElementById('treeId').value;
            const payload = {
                species: document.getElementById('treeSpecies').value,
                location: document.getElementById('treeLocation').value,
                plantedDate: document.getElementById('treeDate').value,
                driveId: parseInt(document.getElementById('treeDrive').value),
                volunteerId: parseInt(document.getElementById('treeVol').value)
            };
            const method = id ? 'PUT' : 'POST';
            const url = id ? `/trees/${id}` : '/trees';
            await app.fetchAPI(url, { method, body: JSON.stringify(payload) });
            app.ui.closeModal('treeModal');
            app.ui.showToast(`Tree ${id ? 'updated' : 'created'} successfully`);
            this.load();
        },
        async edit(id) {
            const t = await app.fetchAPI(`/trees/${id}`);
            document.getElementById('treeId').value = t.treeId;
            document.getElementById('treeSpecies').value = t.species;
            document.getElementById('treeLocation').value = t.location;
            document.getElementById('treeDate').value = t.plantedDate;
            await this.populateSelects();
            document.getElementById('treeDrive').value = t.drive.driveId;
            document.getElementById('treeVol').value = t.volunteer.volunteerId;
            document.getElementById('treeModalTitle').innerText = 'Edit Tree';
            app.ui.openModal('treeModal');
        },
        async delete(id) {
            if(confirm('Delete this tree?\nThis action cannot be undone.')) {
                await app.fetchAPI(`/trees/${id}`, { method: 'DELETE' });
                app.ui.showToast('Tree deleted successfully');
                this.load();
            }
        }
    },
    
    filterTrees() {
        const term = document.getElementById('treeSearch').value.toLowerCase();
        const filtered = app.state.trees.filter(t => 
            t.species.toLowerCase().includes(term) || 
            t.location.toLowerCase().includes(term) ||
            t.treeId.toString().includes(term)
        );
        app.trees.render(filtered);
    },
    
    filterDrives() {
        const term = document.getElementById('driveSearch').value.toLowerCase();
        const filtered = app.state.drives.filter(d => 
            d.driveName.toLowerCase().includes(term) || 
            d.location.toLowerCase().includes(term)
        );
        app.drives.render(filtered);
    },
    
    filterVolunteers() {
        const term = document.getElementById('volSearch').value.toLowerCase();
        const filtered = app.state.volunteers.filter(v => 
            v.name.toLowerCase().includes(term) || 
            v.email.toLowerCase().includes(term)
        );
        app.volunteers.render(filtered);
    },

    checkins: {
        async load() {
            const checkins = await app.fetchAPI('/check-ins');
            const tbody = document.getElementById('checkins-table');
            tbody.innerHTML = checkins.map(c => `
                <tr>
                    <td>#${c.checkinId}</td>
                    <td><strong>Tree #${c.tree.treeId}</strong> - ${c.tree.species}</td>
                    <td>${c.volunteer.name}</td>
                    <td>${c.checkinDate}</td>
                    <td><span class="status-badge ${c.status.toLowerCase()}">${c.status}</span></td>
                    <td>${c.nextCheckinDate || '-'}</td>
                </tr>
            `).join('');
            
            const trees = await app.fetchAPI('/trees');
            const vols = await app.fetchAPI('/volunteers');
            document.getElementById('ciTree').innerHTML = `<option value="" disabled selected hidden>Select tree</option>` + trees.map(t => `<option value="${t.treeId}" data-status="${t.currentStatus}">#${t.treeId} - ${t.species} (${t.currentStatus})</option>`).join('');
            document.getElementById('ciVol').innerHTML = `<option value="" disabled selected hidden>Select volunteer</option>` + vols.map(v => `<option value="${v.volunteerId}">${v.name}</option>`).join('');
        },
        onTreeSelect() {
            const select = document.getElementById('ciTree');
            const selected = select.options[select.selectedIndex];
            if(selected && selected.getAttribute('data-status') === 'DEAD') {
                app.ui.showToast('Note: This tree is currently marked as DEAD.', 'warning');
            }
        },
        onStatusChange() {
            const status = document.querySelector('input[name="ciStatus"]:checked').value;
            if(status === 'DEAD') {
                document.getElementById('deadWarning').classList.remove('hidden');
                document.getElementById('nextDateGroup').classList.add('hidden');
                document.getElementById('ciNext').value = '';
            } else {
                document.getElementById('deadWarning').classList.add('hidden');
                document.getElementById('nextDateGroup').classList.remove('hidden');
            }
        },
        async save(e) {
            e.preventDefault();
            const payload = {
                treeId: parseInt(document.getElementById('ciTree').value),
                volunteerId: parseInt(document.getElementById('ciVol').value),
                checkinDate: document.getElementById('ciDate').value,
                status: document.querySelector('input[name="ciStatus"]:checked').value,
                remarks: document.getElementById('ciRemarks').value,
                nextCheckinDate: document.getElementById('ciNext').value || null
            };
            
            await app.fetchAPI('/check-ins', { method: 'POST', body: JSON.stringify(payload) });
            app.ui.closeModal('checkinModal');
            app.ui.showToast('Check-in recorded successfully');
            this.load();
        }
    },

    reports: {
        async init() {
            const drives = await app.fetchAPI('/drives');
            document.getElementById('reportDriveSelect').innerHTML = '<option value="" disabled selected hidden>Select plantation drive</option>' + drives.map(d => `<option value="${d.driveId}">${d.driveName}</option>`).join('');
            
            const trees = await app.fetchAPI('/trees');
            const aliveTrees = trees.filter(t => t.currentStatus === 'ALIVE').length;
            const survivalRate = trees.length > 0 ? ((aliveTrees / trees.length) * 100).toFixed(2) : '0.00';
            
            const circle = document.getElementById('report-overall');
            circle.style.background = `conic-gradient(var(--accent) ${survivalRate * 3.6}deg, #e0e0e0 0deg)`;
            circle.querySelector('.percentage').innerText = `${survivalRate}%`;
            
            document.getElementById('report-overall-stats').innerHTML = `
                <div class="rs-item"><span class="text-success">${aliveTrees}</span><p>Alive</p></div>
                <div class="rs-item"><span class="text-danger">${trees.length - aliveTrees}</span><p>Dead</p></div>
                <div class="rs-item"><span>${trees.length}</span><p>Total</p></div>
            `;
        },
        async fetchByDrive() {
            const id = document.getElementById('reportDriveSelect').value;
            if(!id) return;
            const rate = await app.fetchAPI(`/reports/drives/${id}/survival-rate`);
            const fill = document.querySelector('#report-drive-result .progress-fill');
            fill.style.width = '0%';
            setTimeout(() => fill.style.width = `${rate}%`, 100);
            document.querySelector('#report-drive-result .res-text').innerText = `${rate}%`;
        },
        async fetchBySpecies() {
            const species = document.getElementById('reportSpeciesInput').value;
            if(!species) return;
            const rate = await app.fetchAPI(`/reports/species/${species}/survival-rate`);
            const fill = document.querySelector('#report-species-result .progress-fill');
            fill.style.width = '0%';
            setTimeout(() => fill.style.width = `${rate}%`, 100);
            document.querySelector('#report-species-result .res-text').innerText = `${rate}%`;
        }
    }
};

document.addEventListener('DOMContentLoaded', () => app.init());
